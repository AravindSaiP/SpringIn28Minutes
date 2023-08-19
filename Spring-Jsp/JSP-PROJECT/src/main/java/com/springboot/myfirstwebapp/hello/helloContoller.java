package com.springboot.myfirstwebapp.hello;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class helloContoller {

    @RequestMapping("/hello-jsp")
    public String sayHelloJsp(){
        return "sayHello";
    }
}
