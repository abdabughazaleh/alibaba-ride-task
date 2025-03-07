package com.alibaba.drivermanagement.model.dto;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record AcceptRideRequest(String transactionNo, String driver, String driverMobileNo) implements Serializable {
}