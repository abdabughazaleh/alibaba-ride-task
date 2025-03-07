package com.alibaba.ride;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class RideApplication {

    public static void main(String[] args) {
        SpringApplication.run(RideApplication.class, args);
    }

}
