package com.learn_spring_boot.basic_restapi.currency_controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyController {

    @Autowired
    private CurrencyServiceConfiguration currencyServiceConfiguration;

    @RequestMapping("/currency-configurations")
    public CurrencyServiceConfiguration retrieveCurrencyConfigurations(){
      return currencyServiceConfiguration;
    }
}
