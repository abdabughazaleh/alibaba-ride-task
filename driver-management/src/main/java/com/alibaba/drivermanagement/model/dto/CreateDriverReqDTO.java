package com.alibaba.drivermanagement.model.dto;

public record CreateDriverReqDTO(String name, String username, String password, String licenseNumber, String mobileNo) {
}
