package com.example.RESTAPI.SocialMediaApplication.HelloWorld;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class HelloWorldContoller {

    private MessageSource messageSource;

    public HelloWorldContoller(MessageSource messageSource){
        this.messageSource = messageSource;
    }

    @GetMapping(path = "/hello")
    public String hello(){
        return "hello";
    }

    @GetMapping(path = "/hello-international")
    public String hello_international(){

        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage("good.morning.message",null,"Default Message", locale);
        //return "Good morning";
    }
}
