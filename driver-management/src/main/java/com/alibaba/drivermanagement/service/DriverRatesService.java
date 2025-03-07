package com.alibaba.drivermanagement.service;

import com.alibaba.drivermanagement.model.dto.DriverDTO;
import com.alibaba.drivermanagement.model.dto.SubmitRateDTO;
import org.springframework.stereotype.Service;

@Service
public interface DriverRatesService {
    DriverDTO submitRate(SubmitRateDTO dto);
}
