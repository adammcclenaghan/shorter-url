package com.example.accessingmongodbdatarest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController
{
    @RequestMapping("/")
    public String getGreeting()
    {
        return "Hello its adam";
    }
}
