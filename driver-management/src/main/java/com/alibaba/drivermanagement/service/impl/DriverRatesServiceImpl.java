package com.alibaba.drivermanagement.service.impl;

import com.alibaba.drivermanagement.model.dto.DriverDTO;
import com.alibaba.drivermanagement.model.dto.SubmitRateDTO;
import com.alibaba.drivermanagement.model.entity.DriverRates;
import com.alibaba.drivermanagement.model.repository.DriverRatesRepository;
import com.alibaba.drivermanagement.service.DriverRatesService;
import com.alibaba.drivermanagement.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DriverRatesServiceImpl implements DriverRatesService {
    private final DriverRatesRepository driverRatesRepository;
    private final DriverService driverService;

    @Override
    public DriverDTO submitRate(SubmitRateDTO dto) {
        DriverRates driverRates = DriverRates.builder()
                .rate(dto.rate())
                .driver(dto.driver())
                .rider(dto.rider())
                .rideTranNo(dto.tranNo())
                .build();
        this.driverRatesRepository.save(driverRates);
        updateDriverRate(dto.driver());
        return this.driverService.getDriverDetailsByUsername(dto.driver());
    }

    private void updateDriverRate(String driver) {
        Double rate = this.driverRatesRepository.avgRatesByDriver(driver);
        this.driverService.updateDriverRate(driver, rate);
    }
}
