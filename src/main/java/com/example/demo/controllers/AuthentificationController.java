package com.example.demo.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthentificationController {

    @GetMapping("hi")
    public String sayHi(){
        String s = "hi please work";
        System.out.println(s);
        return s;
    }
}
