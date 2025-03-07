package com.alibaba.drivermanagement.controller;

import com.alibaba.drivermanagement.model.dto.*;
import com.alibaba.drivermanagement.service.RideRequestProducerService;
import com.alibaba.drivermanagement.service.RideService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

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

    @GetMapping("/rides")
    @CircuitBreaker(name = "rideService", fallbackMethod = "getDriverRidesFallback")
    ResponseEntity<List<RideDTO>> getDriverRides(@RequestParam("pageNumber") Integer pageNumber,
                                                 @RequestParam("pageSize") Integer pageSize) {
        return ResponseEntity.ok(rideService.getDriverRides(pageNumber, pageSize));
    }

    public ResponseEntity<List<RideDTO>> getDriverRidesFallback(Throwable throwable) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Collections.emptyList());
    }
}
