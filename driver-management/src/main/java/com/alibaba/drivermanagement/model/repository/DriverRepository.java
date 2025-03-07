package com.alibaba.drivermanagement.model.repository;

import com.alibaba.drivermanagement.model.entity.Driver;
import com.alibaba.drivermanagement.model.enums.DriverStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DriverRepository extends JpaRepository<Driver, Long> {
    Optional<Driver> findByMobileNo(String mobileNo);
    Optional<Driver> findByUsername(String username);
    List<Driver> findAllByStatus(DriverStatus status);
}
