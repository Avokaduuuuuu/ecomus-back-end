package com.avocado.ecomus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EcomusApplication {
    public static void main(String[] args) {
        SpringApplication.run(EcomusApplication.class, args);
    }
}


