package com.springboot.myfirstwebapp.login;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("name")
public class loginController {

    private AuthenticationService authenticationService;

    public loginController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
    //    @RequestMapping("/login")
//    public String loginPage(@RequestParam String name, ModelMap modelMap){
//        modelMap.put("name",name);
//        return "login";
//    }

        @RequestMapping(value="login",method = RequestMethod.GET)
        public String gotToLoginPage(){
           return "login";
        }

        @RequestMapping(value = "login",method = RequestMethod.POST)
        public String gotToWelcomePage(@RequestParam String name, @RequestParam String password, ModelMap modelMap){
            if(authenticationService.authenticate(name,password)) {
                modelMap.put("name", name);
                return "welcome";
            }
            else {
                modelMap.put("errorMessage","Invalid credentials! Please provide correct ones");
                return "login";
            }
        }


}
