package com.alibaba.drivermanagement.controller;

import com.alibaba.drivermanagement.model.dto.*;
import com.alibaba.drivermanagement.service.DriverRatesService;
import com.alibaba.drivermanagement.service.DriverService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/driver")
@RequiredArgsConstructor
public class DriverController {
    private final DriverService driverService;
    private final DriverRatesService driverRatesService;

    @PostMapping("/sign-up")
    @CircuitBreaker(name = "authService", fallbackMethod = "authFallback")
    public ResponseEntity<CreateDriverRespDTO> createDriver(@Valid @RequestBody CreateDriverReqDTO reqDTO) {
        return ResponseEntity.ok(driverService.createDriver(reqDTO));
    }

    @PutMapping
    public ResponseEntity<UpdateDriverStatusRespDTO> updateDriverStatus(@Valid @RequestBody UpdateDriverStatusReqDTO reqDTO) {
        return ResponseEntity.ok(driverService.updateDriverStatus(reqDTO));
    }

    @PostMapping("/rate")
    public ResponseEntity<DriverDTO> updateDriverRate(@Valid @RequestBody SubmitRateDTO reqDTO) {
        return ResponseEntity.ok(driverRatesService.submitRate(reqDTO));
    }

    public ResponseEntity<List<RideDTO>> authFallback(Throwable throwable) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(null);
    }

}
