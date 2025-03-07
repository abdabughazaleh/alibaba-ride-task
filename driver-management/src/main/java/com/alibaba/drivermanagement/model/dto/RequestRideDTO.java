package com.alibaba.drivermanagement.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public record RequestRideDTO(String rider, String locatedAt, String dropAt, BigDecimal amount, String transactionNo) implements Serializable {
}