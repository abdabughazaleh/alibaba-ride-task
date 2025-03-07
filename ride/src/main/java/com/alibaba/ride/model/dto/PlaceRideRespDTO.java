package com.alibaba.ride.model.dto;

import java.math.BigDecimal;

public record PlaceRideRespDTO(String locatedAt, String dropAt, BigDecimal amount, String transactionNo) {
}
