package com.alibaba.drivermanagement.controller;

import com.alibaba.drivermanagement.model.dto.*;
import com.alibaba.drivermanagement.service.RideRequestProducerService;
import com.alibaba.drivermanagement.service.RideService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ride")
@RequiredArgsConstructor
public class RideController {
    private final RideService rideService;

    @PostMapping("/accept")
    public ResponseEntity<AcceptRideResponse> acceptRide(@RequestBody DriverAcceptRideReqDTO dto) {
        AcceptRideResponse acceptRideResponse = this.rideService.acceptRide(dto);
        return ResponseEntity.ok(acceptRideResponse);
    }

    @PostMapping("/complete")
    public ResponseEntity<CompleteRideResponse> completeRide(@RequestBody DriverCompleteRideReqDTO dto) {
        CompleteRideResponse acceptRideResponse = this.rideService.completeRide(dto);
        return ResponseEntity.ok(acceptRideResponse);
    }
}
