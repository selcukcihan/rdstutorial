package com.selcukcihan.tutor.rds;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RdsApplication implements ApplicationRunner {
    private ApplicationArguments applicationArguments;

    public static void main(String[] args) {
        SpringApplication.run(RdsApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.applicationArguments = applicationArguments;
    }


    @Bean(name = "propertiesFile")
    public String getPropertiesFile() {
        return "sql.properties";
    }
}
