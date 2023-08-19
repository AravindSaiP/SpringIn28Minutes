package com.springboot.myfirstwebapp.login;

import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    public boolean authenticate(String username, String password){
        boolean isValidUserName = username.equalsIgnoreCase("user");
        boolean isValidPassword = password.equalsIgnoreCase("1234");
        return isValidUserName && isValidPassword;
    }
}
