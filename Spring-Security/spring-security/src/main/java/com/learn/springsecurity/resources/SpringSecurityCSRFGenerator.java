package com.learn.springsecurity.resources;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringSecurityCSRFGenerator {

    @GetMapping("/csrf")
    public CsrfToken getCsRfToken(HttpServletRequest request){
        return (CsrfToken) request.getAttribute("_csrf");
    }
}
