package com.example.phishingbackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.phishingbackend.mapper") // 必须加这一行！
public class PhishingBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(PhishingBackendApplication.class, args);
    }

}
