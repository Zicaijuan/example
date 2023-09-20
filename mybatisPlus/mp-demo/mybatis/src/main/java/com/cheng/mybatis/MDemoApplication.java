package com.cheng.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@MapperScan("com.cheng.mybatis.mapper")
@SpringBootApplication
public class MDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MDemoApplication.class, args);
    }

}

