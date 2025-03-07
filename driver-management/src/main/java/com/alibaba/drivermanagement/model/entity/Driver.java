package com.alibaba.drivermanagement.model.entity;

import com.alibaba.drivermanagement.model.enums.DriverStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "driver" , schema = "drivers")
@Entity
@Builder
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "driver_id")
    private Long driverId;
    private String name;
    private String username;
    @Column(name = "license_number")
    private String licenseNumber;
    @Column(name = "rating" , nullable = true)
    private Integer rating;
    @Column(name = "mobile_no")
    private String mobileNo;
    @Enumerated(EnumType.STRING)
    @Column(name = "status" , nullable = true)
    private DriverStatus status;
}
