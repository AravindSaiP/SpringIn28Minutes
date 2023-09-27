package com.learn.springsecurity.configuration.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.stream.Collectors;

//@RestController
public class JwtAuthenticationResource {

    private JwtEncoder jwtEncoder;

    public JwtAuthenticationResource(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    @GetMapping("/authenticate")
    //It is for understanding what Authentication object has and by using which we create JWT
    public Authentication authentication(Authentication authentication){
        return authentication;
    }

    @GetMapping("/getJwtToken")
    public JwtResponse getAuthenticationToken(Authentication authentication){
        return new JwtResponse(createToken(authentication));
    }

    private String createToken(Authentication authentication) {
        var claims = JwtClaimsSet.builder()
                .issuer("self") //Who is creating the JWT
                .issuedAt(Instant.now()) //Time when JWT is created
                .expiresAt(Instant.now().plusSeconds(60 * 30)) //JWT expires at
                .subject(authentication.getName()) //Some additional data here we are sending logged-in username
                .claim("scope", createScope(authentication)) //We are sending the role of the user example:- Admin,user etc--
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue(); //we are making use of Jwt encode a bean that is available in jwt security configuration



    }

    private String createScope(Authentication authentication) {

        return authentication.getAuthorities().stream() //getAuthorities will give array of objects we are putting stream on it
                .map(authorityObj -> authorityObj.getAuthority())
                .collect(Collectors.joining(" "));
    }

}

record JwtResponse(String token){};
