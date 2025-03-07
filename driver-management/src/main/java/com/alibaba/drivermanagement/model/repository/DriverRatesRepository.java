package com.alibaba.drivermanagement.model.repository;

import com.alibaba.drivermanagement.model.entity.DriverRates;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DriverRatesRepository extends JpaRepository<DriverRates, Long> {
    @Query("SELECT AVG(dr.rate) FROM DriverRates dr WHERE dr.driver = :driver")
    Double avgRatesByDriver(@Param("driver") String driver);
}
