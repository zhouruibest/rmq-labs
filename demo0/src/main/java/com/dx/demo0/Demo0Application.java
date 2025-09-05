package com.dx.demo0;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync //  开启异步任务
public class Demo0Application {

    public static void main(String[] args) {
        SpringApplication.run(Demo0Application.class, args);
    }

}
