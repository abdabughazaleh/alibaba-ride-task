package com.alibaba.ride.model.dto;

import lombok.Builder;

import java.io.Serializable;
import java.math.BigDecimal;

@Builder
public record RequestCancelDTO(String transactionNo) implements Serializable {
}