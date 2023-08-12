package com.learn_spring_boot.basic_restapi.currency_controller;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

//currency-service-url:"https://default.com"
//        currency-service-username:"deafult"
//        currency-service-key:"default_key"
@ConfigurationProperties(prefix = "currency-service")
@Component
public class CurrencyServiceConfiguration {

    private String url;
    private String username;
    private String key;

//    public CurrencyServiceConfiguration(String url, String name, String key) {
//        this.url = url;
//        this.name = name;
//        this.key = key;
//    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
