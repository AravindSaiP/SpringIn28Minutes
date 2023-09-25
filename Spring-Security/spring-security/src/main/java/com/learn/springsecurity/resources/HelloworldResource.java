package com.learn.springsecurity.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloworldResource {

    @GetMapping("/hello-world")
    public String helloWorld() {
        return "Hello World";
    }
}
