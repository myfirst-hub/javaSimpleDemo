package com.example.simpleDemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SimpleDemoApplication {

    private static final Logger logger = LoggerFactory.getLogger(SimpleDemoApplication.class);

    public static void main(String[] args) {
        logger.info("This is an info message................", SimpleDemoApplication.class);
        SpringApplication.run(SimpleDemoApplication.class, args);
    }

}
