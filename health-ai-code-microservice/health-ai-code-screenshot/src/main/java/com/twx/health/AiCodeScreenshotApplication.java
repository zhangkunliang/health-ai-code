package com.twx.health;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class AiCodeScreenshotApplication {
    public static void main(String[] args) {
        SpringApplication.run(AiCodeScreenshotApplication.class, args);
    }
}