package com.sean.highconcurrencylikesystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.sean.highconcurrencylikesystem.mapper")
public class HighConcurrencyLikeSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(HighConcurrencyLikeSystemApplication.class, args);
    }

}
