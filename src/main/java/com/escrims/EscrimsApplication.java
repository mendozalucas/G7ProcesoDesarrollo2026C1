package com.escrims;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EscrimsApplication {

    public static void main(String[] args) {
        SpringApplication.run(EscrimsApplication.class, args);
    }
}
