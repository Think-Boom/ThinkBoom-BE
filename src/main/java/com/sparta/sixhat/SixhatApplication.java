package com.sparta.sixhat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SixhatApplication {

    public static void main(String[] args) {
        SpringApplication.run(SixhatApplication.class, args);
    }

}
