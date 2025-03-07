package com.alibaba.drivermanagement.service.impl;

import com.alibaba.drivermanagement.advice.ErrorHandler;
import com.alibaba.drivermanagement.model.dto.*;
import com.alibaba.drivermanagement.proxy.RideProxy;
import com.alibaba.drivermanagement.service.DriverService;
import com.alibaba.drivermanagement.service.RideRequestProducerService;
import com.alibaba.drivermanagement.service.RideService;
import com.alibaba.drivermanagement.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RideServiceImpl implements RideService {
    private final RideRequestProducerService rideRequestProducerService;
    private final DriverService driverService;
    private final JwtUtil jwtUtil;
    private final RideProxy rideProxy;

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

    @Override
    public List<RideDTO> getDriverRides(Integer pageNumber, Integer pageSize) {
        ResponseEntity<List<RideDTO>> driverRides = this.rideProxy.getDriverRides(jwtUtil.extractUsername(), pageNumber, pageSize);
        if (!driverRides.getStatusCode().is2xxSuccessful()) {
            throw new ErrorHandler.CustomBadRequest(ErrorHandler.SystemErrors.SOMETHING_WRONG_WHILE_GETTING_DRIVER_RIDES);
        }
        return driverRides.getBody();
    }
}
