package com.martin.car.runner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.martin.car")
@EntityScan("com.martin.car.model")
@EnableJpaRepositories("com.martin.car.core")
public class CarApplication {
    public static void main(String[] args) {
        SpringApplication.run(CarApplication.class);
    }
}
