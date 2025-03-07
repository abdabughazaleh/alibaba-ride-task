package com.alibaba.drivermanagement.model.dto;

public record CreateDriverRespDTO(String name,
                                  String username,
                                  String licenseNumber,
                                  String mobileNo) {
}
