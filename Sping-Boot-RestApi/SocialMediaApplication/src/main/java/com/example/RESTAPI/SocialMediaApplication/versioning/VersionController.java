package com.example.RESTAPI.SocialMediaApplication.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersionController {

    //URI Versioning --> Used by twitter
    //http://localhost:8080/v1/person
    @GetMapping(path = "/v1/person")
    public Personv1 getFirstVersionOfPerson(){
        return new Personv1("Bob john");
    }

    //http://localhost:8080/v2/person
    @GetMapping(path = "/v2/person")
    public Personv2 getSecondVersionOfPerson(){
        return new Personv2(new Name("Bob","John"));
    }

    //Request parameter version ---> Used by Amazon
    //http://localhost:8080/person?version=1
    @GetMapping(path = "/person",params = "version=1")
    public Personv1 getFirstVersionOfPersonRequestParameter(){
        return new Personv1("Bob john");
    }

    //http://localhost:8080/person?version=2
    @GetMapping(path = "/person",params = "version=2")
    public Personv2 getSecondVersionOfPersonRequestParameter(){
        return new Personv2(new Name("Bob","John"));
    }

    //Custom headers version ---> Used by Microsoft
    //http://localhost:8080/person
    //X-API-VERSION:1 it's a header
    @GetMapping(path = "/person",headers = "X-API-VERSION=1")
    public Personv1 getFirstVersionOfPersonRequestHeader(){
        return new Personv1("Bob john");
    }


    //http://localhost:8080/person
    //X-API-VERSION:2     it's a header
    @GetMapping(path = "/person",headers = "X-API-VERSION=2")
    public Personv2 getSecondVersionOfPersonRequestHeader(){
        return new Personv2(new Name("Bob","John"));
    }

    ////Multi media versioning ----> Github
    //http://localhost:8080/person
    //Accept:application/vnd.company.app-v1+json
    @GetMapping(path = "/person",produces = "application/vnd.company.app-v1+json")
    public Personv1 getFirstVersionOfPersonAcceptHeader(){
        return new Personv1("Bob john");
    }


    //http://localhost:8080/person
    //Accept:application/vnd.company.app-v2+json
    @GetMapping(path = "/person",produces = "application/vnd.company.app-v2+json")
    public Personv2 getSecondVersionOfPersonAcceptHeader(){
        return new Personv2(new Name("Bob","John"));
    }




}


