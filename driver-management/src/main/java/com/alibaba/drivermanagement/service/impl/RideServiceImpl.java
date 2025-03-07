package com.alibaba.drivermanagement.service.impl;

import com.alibaba.drivermanagement.model.dto.*;
import com.alibaba.drivermanagement.service.DriverService;
import com.alibaba.drivermanagement.service.RideRequestProducerService;
import com.alibaba.drivermanagement.service.RideService;
import com.alibaba.drivermanagement.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RideServiceImpl implements RideService {
    private final RideRequestProducerService rideRequestProducerService;
    private final DriverService driverService;
    private final JwtUtil jwtUtil;

    @Override
    public AcceptRideResponse acceptRide(DriverAcceptRideReqDTO request) {
        String username = this.jwtUtil.extractUsername();
        DriverDTO driver = driverService.getDriverDetailsByUsername(username);
        return rideRequestProducerService.sendAcceptRideEvent(new AcceptRideRequest(request.tranNo(), driver.getUsername(), driver.getMobileNo()));
    }

    @Override
    public CompleteRideResponse completeRide(DriverCompleteRideReqDTO dto) {
        CompleteRideResponse completeRideResponse = this.rideRequestProducerService.sendCompleteRideEvent(dto);
        return completeRideResponse;
    }
}
