package com.app.travel_mate.ranking_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.app.travel_mate.ranking_service",
        "com.app.travel_mate.domain",
        "com.app.travel_mate"
})
public class RankingServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(RankingServiceApplication.class, args);
    }
}
