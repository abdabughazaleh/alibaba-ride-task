package com.alibaba.ride.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public record RequestDriverRateDTO(
        @NotNull(message = "Transaction cannot be null")
        String tranNo,
        @Max(value = 5, message = "Rate must be less than or equal to 5")
        Integer rate) implements Serializable {
}
