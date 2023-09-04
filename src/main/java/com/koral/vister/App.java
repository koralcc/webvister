package com.koral.vister;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
@MapperScan("com.koral.vister.mapper")
public class App {
    public static void main(String[] args) {

        SpringApplication.run(App.class);
    }
}
