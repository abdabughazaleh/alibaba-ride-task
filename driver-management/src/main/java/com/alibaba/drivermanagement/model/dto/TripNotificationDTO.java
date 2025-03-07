package com.alibaba.drivermanagement.model.dto;

import com.alibaba.drivermanagement.model.enums.TripNotificationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TripNotificationDTO {
    private String locatedAt;
    private String dropAt;
    private TripNotificationStatus status;
    private BigDecimal amount;
    private String tripTranNo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
