package com.alibaba.ride.model.dto;

import com.alibaba.ride.model.enums.PaymentMethods;

import java.math.BigDecimal;

public record PlaceRideDTO(String locatedAt, String dropAt, BigDecimal amount, PaymentMethods paymentMethod) {
}
