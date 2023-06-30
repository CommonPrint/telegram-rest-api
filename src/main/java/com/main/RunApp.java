package com.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;


@SpringBootApplication
@EntityScan
public class RunApp {

    public static void main(String[] args) {
        SpringApplication.run(RunApp.class, args);
    }
}
