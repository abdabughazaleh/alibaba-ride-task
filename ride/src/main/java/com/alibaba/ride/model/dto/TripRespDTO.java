package com.alibaba.ride.model.dto;


import com.alibaba.ride.model.enums.TripStatus;
import lombok.Builder;

import java.io.Serializable;
import java.math.BigDecimal;

@Builder
public record TripRespDTO(
        String rider,
        BigDecimal amount,
        String locatedAt,
        String dropAt,
        String driver,
        String driverName,
        String driverNo,
        String transactionNo,
        TripStatus status) implements Serializable {
}
