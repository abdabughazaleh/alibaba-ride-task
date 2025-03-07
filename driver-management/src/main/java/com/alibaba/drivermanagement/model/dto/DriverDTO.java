package com.alibaba.drivermanagement.model.dto;

import com.alibaba.drivermanagement.model.enums.DriverStatus;
import jakarta.persistence.*;
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
    private Integer rating;
    private String mobileNo;
    private DriverStatus status;
}
