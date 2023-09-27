package com.learn.springsecurity.configuration.jwt;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

import static org.springframework.security.config.Customizer.withDefaults;

//@Configuration
public class jwtSecurityConfiguration {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated());
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.httpBasic(Customizer.withDefaults());
        http.csrf(csrf->csrf.disable());
        http.headers().frameOptions().sameOrigin();
        http.oauth2ResourceServer((oauth2) -> oauth2.jwt(withDefaults())); //Enabling Oauth2 for JWT Usage
        return http.build();
    }

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

    @Bean
    //STEP 1:- CREATE KEY PAIR
    public KeyPair keyPair(){
        try {
            var keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);  //WE NEED TO GIVE KEY SIZE
            return keyPairGenerator.generateKeyPair();
        }
        catch (NoSuchAlgorithmException exception){
            throw new RuntimeException(exception);
        }

    }

    @Bean
    //STEP 2:- We are creating RSA key object using key pair
    public RSAKey rsaKey(KeyPair keyPair) {

        return new RSAKey
                .Builder((RSAPublicKey)keyPair.getPublic()) //we are sending public key from key pair
                .privateKey(keyPair.getPrivate()) //we are sending private key from key pair
                .keyID(UUID.randomUUID().toString())
                .build();
    }

    @Bean
    //STEP 3:- Creating Jwk Source
    public JWKSource<SecurityContext> jwkSource(RSAKey rsaKey) {
        var jwkSet = new JWKSet(rsaKey);

        return (jwkSelector, context) ->  jwkSelector.select(jwkSet);

    }

    @Bean
    //STEP 4:- Use RSA public key for decoding
    public JwtDecoder jwtDecoder(RSAKey rsaKey) throws JOSEException {
        return NimbusJwtDecoder
                .withPublicKey(rsaKey.toRSAPublicKey())
                .build();

    }

    @Bean
    //We need an encoder to create JWT token for the client
    public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
        return new NimbusJwtEncoder(jwkSource);
    }


}
