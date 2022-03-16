package com.steepcliff.thinkboom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ThinkBoomApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThinkBoomApplication.class, args);
    }

}
