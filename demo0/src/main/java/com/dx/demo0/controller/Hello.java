package com.dx.demo0.controller;


import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class Hello {
    @GetMapping("/")
    public String hello() {
        return "hello";
    }
}
