package com.alibaba.drivermanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DriverManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(DriverManagementApplication.class, args);
    }

}
