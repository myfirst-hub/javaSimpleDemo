package com.example.simoledemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SimoleDemoApplication {

    private static final Logger logger = LoggerFactory.getLogger(SimoleDemoApplication.class);

    public static void main(String[] args) {
        logger.info("This is an info message................", SimoleDemoApplication.class);
        SpringApplication.run(SimoleDemoApplication.class, args);
    }

}
