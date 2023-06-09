package com.example.flower_fight;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class FlowerFightApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlowerFightApplication.class, args);
    }
}
