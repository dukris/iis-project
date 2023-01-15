package com.solvd.laba.iis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.solvd.laba.iis.persistence.mybatis")
public class IisApplication {

    public static void main(String[] args) {
        SpringApplication.run(IisApplication.class, args);
    }

}
