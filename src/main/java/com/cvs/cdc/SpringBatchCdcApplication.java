package com.cvs.cdc;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class SpringBatchCdcApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBatchCdcApplication.class, args);
    }

}
