package com.alibaba.drivermanagement.service;

import com.alibaba.drivermanagement.model.dto.*;
import com.alibaba.drivermanagement.model.enums.DriverStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DriverService {
    CreateDriverRespDTO createDriver(CreateDriverReqDTO reqDTO);
    UpdateDriverStatusRespDTO updateDriverStatus(UpdateDriverStatusReqDTO reqDTO);
    List<DriverDTO> getDriversByStatus(DriverStatus status);
    DriverDTO getDriverDetailsByUsername(String username);
    DriverDTO updateDriverRate(String username , Double newRate);
   /* DriverDTO submitDriverRate(SubmitRateDTO dto);*/
}
