package com.honwaii.bigdata.sparklearning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SparklearningApplication {

    public static void main(String[] args) {
        SpringApplication.run(SparklearningApplication.class, args);
    }
}
