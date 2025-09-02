package com.dx.demo0.controller;


import com.dx.demo0.entity.HelloResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class Hello {
    @GetMapping("/")
    public HelloResponse hello() {
        return new HelloResponse("hello");
    }
}
