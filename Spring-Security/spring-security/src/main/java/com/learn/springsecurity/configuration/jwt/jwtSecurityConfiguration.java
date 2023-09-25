package com.learn.springsecurity.configuration.jwt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

@Configuration
public class jwtSecurityConfiguration {

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
