package com.alibaba.drivermanagement.model.dto;

import com.alibaba.drivermanagement.model.enums.RideStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RideDTO implements Serializable {
    private String name;
    private String username;
    private String mobileNo;
    private String locatedAt;
    private String dropAt;
    private RideStatus status;
    private String driver;
    private BigDecimal amount;
    private String driverMobileNo;
    private String transactionNo;
}
