package com.alibaba.ride.model.dto;

import lombok.Builder;

import java.io.Serializable;
import java.math.BigDecimal;
@Builder
public record RequestRideDTO(String rider, String locatedAt, String dropAt, BigDecimal amount, String transactionNo) implements Serializable {
}