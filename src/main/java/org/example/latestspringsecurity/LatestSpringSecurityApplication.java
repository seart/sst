package org.example.latestspringsecurity;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("org.example.latestspringsecurity.dao")
@SpringBootApplication
public class LatestSpringSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(LatestSpringSecurityApplication.class, args);
    }

}
