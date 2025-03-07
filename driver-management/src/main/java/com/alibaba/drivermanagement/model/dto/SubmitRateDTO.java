package com.alibaba.drivermanagement.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public record SubmitRateDTO(
        @NotNull(message = "Transaction cannot be null")
        String tranNo,
        @NotNull(message = "Rider cannot be null")
        String rider,
        @NotNull(message = "Driver cannot be null")
        String driver,
        @NotNull(message = "Rate cannot be null")
        @Max(value = 5, message = "Rate must be less than or equal to 5")
        Integer rate) implements Serializable {
}
