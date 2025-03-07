package com.alibaba.drivermanagement.service;

import com.alibaba.drivermanagement.model.dto.AcceptRideResponse;
import com.alibaba.drivermanagement.model.dto.CompleteRideResponse;
import com.alibaba.drivermanagement.model.dto.DriverAcceptRideReqDTO;
import com.alibaba.drivermanagement.model.dto.DriverCompleteRideReqDTO;
import org.springframework.stereotype.Service;

@Service
public interface RideService {
    AcceptRideResponse acceptRide(DriverAcceptRideReqDTO request);

    CompleteRideResponse completeRide(DriverCompleteRideReqDTO dto);
}
