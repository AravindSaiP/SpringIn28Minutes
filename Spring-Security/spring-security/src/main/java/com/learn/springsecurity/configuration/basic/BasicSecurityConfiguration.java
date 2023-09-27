package com.learn.springsecurity.configuration.basic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableMethodSecurity(jsr250Enabled = true, securedEnabled = true)
public class BasicSecurityConfiguration {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> {

            auth
                    /*********************************************************/
                    //This is an example of Global security authorization
                    //.requestMatchers("/users").hasAnyRole("USER") //Only users with role USER can access the URL /users
                    //.requestMatchers("/admin/**").hasRole("ADMIN") //Only users with role ADMIN can access the URL /admins
                    /************************************************************/

                    .anyRequest().authenticated();
        });
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.httpBasic(withDefaults());
        http.csrf(csrf->csrf.disable());
        http.headers().frameOptions().sameOrigin();
        return http.build();
    }

    //This is to use in memory configuration
//    @Bean
//    public UserDetailsService userDetailsService(){
//        var user = User.withUsername("user")
//                .password("{noop}dummy")
//                .roles("USER")
//                .build();
//
//        var admin = User.withUsername("admin")
//                .password("{noop}dummy")
//                .roles("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(user,admin);
//    }

    //For JDBC

    @Bean
    public DataSource dataSource(){
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2) //It is to specify that we are using h2 db
                .addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION) //It is to specify that to run the script at the start. DEFAULT_USER_SCHEMA_DDL_LOCATION ---> It is a predefined ddl command that creates user and authorities table
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource){ //Here dataSource bean will be injected
        var user = User.withUsername("user")
                //.password("{noop}dummy")
                .password("dummy")
                .passwordEncoder(password -> passwordEncoder().encode(password))
                .roles("USER")
                .build();

        var admin = User.withUsername("admin")
                //.password("{noop}dummy")
                .password("dummy")
                .passwordEncoder(password -> passwordEncoder().encode(password))
                .roles("ADMIN")
                .build();

        var jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.createUser(user);
        jdbcUserDetailsManager.createUser(admin);

        return jdbcUserDetailsManager;
    }

    @Bean
    //PasswordEncoder is the interface
    // BCryptPasswordEncoder is the impl of PasswordEncoder
    // PasswordEncoder may be named as encoder by it actually do hashing
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
