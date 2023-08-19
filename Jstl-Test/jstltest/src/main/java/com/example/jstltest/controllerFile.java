package com.example.jstltest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class controllerFile {

    @RequestMapping("hello")
    public String helloPage(ModelMap map){
        map.put("message","How are you?");
        return "hello";
    }
}
