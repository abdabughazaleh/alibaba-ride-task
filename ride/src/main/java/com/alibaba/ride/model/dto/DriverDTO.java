package com.alibaba.ride.model.dto;

import com.alibaba.ride.model.enums.DriverStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverDTO {
    private String name;
    private String username;
    private String licenseNumber;
    private Double rating;
    private String mobileNo;
    private DriverStatus status;
}
