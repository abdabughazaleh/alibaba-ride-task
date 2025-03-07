package com.alibaba.drivermanagement.model.dto;

import com.alibaba.drivermanagement.model.enums.DriverStatus;

public record UpdateDriverStatusReqDTO(DriverStatus status) {
}
