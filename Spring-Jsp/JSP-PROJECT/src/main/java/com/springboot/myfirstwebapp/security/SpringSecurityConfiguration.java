package com.springboot.myfirstwebapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.jaas.memory.InMemoryConfiguration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.net.http.HttpRequest;
import java.util.function.Function;

@Configuration
public class SpringSecurityConfiguration {

    private String password = "dummy";
    private String user = "user";

    @Bean
    public InMemoryUserDetailsManager createUserDetailsManager(){
//        UserDetails userDetails = User
//                .withDefaultPasswordEncoder()
//                .username("user")
//                .password("dummy")
//                .roles("USER","ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(userDetails);

        UserDetails userDetails1 = createNewUser("user","dummy");
        UserDetails userDetails2 = createNewUser("in28Minutes","dummy");
        return new InMemoryUserDetailsManager(userDetails1,userDetails2);
    }

    private UserDetails createNewUser(String user, String password) {
        Function<String, String> passwordEncoder = pass -> passwordEncoder().encode(pass);

        UserDetails userDetails = User.builder()
                .passwordEncoder(passwordEncoder)
                .username(user)
                .password(password)
                .roles("USER","ADMIN")
                .build();
        return userDetails;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }




    //BY DEAFULT
    //All URLs are protected
    //A login form is shown for unauthorized requests
    //CSRF is enabled
    //Frames are not allowed
    //This is all done by SecurityFilterChain method
    //We want our own implementation for SecurityFilterChain hence we will be able to access h2

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                auth -> auth.anyRequest().authenticated());
        http.formLogin(Customizer.withDefaults());

        http.csrf().disable();
        http.headers().frameOptions().disable();

        return http.build();

    }

}
