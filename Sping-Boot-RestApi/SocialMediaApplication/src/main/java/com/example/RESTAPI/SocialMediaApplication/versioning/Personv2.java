package com.example.RESTAPI.SocialMediaApplication.versioning;

public class Personv2 {

    private Name name;

    public Personv2(Name name) {
        this.name = name;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Personv2{" +
                "name=" + name +
                '}';
    }
}
