package com.alibaba.drivermanagement.controller;

import com.alibaba.drivermanagement.model.dto.CreateDriverReqDTO;
import com.alibaba.drivermanagement.model.dto.CreateDriverRespDTO;
import com.alibaba.drivermanagement.model.dto.UpdateDriverStatusReqDTO;
import com.alibaba.drivermanagement.model.dto.UpdateDriverStatusRespDTO;
import com.alibaba.drivermanagement.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/driver")
@RequiredArgsConstructor
public class DriverController {
    private final DriverService driverService;

    @PostMapping("/sign-up")
    public ResponseEntity<CreateDriverRespDTO> createDriver(@RequestBody CreateDriverReqDTO reqDTO) {
        return ResponseEntity.ok(driverService.createDriver(reqDTO));
    }

    @PutMapping
    public ResponseEntity<UpdateDriverStatusRespDTO> updateDriverStatus(@RequestBody UpdateDriverStatusReqDTO reqDTO ,
                                                                        @RequestHeader(value = "Authorization") String authorization ) {
        return ResponseEntity.ok(driverService.updateDriverStatus(reqDTO));
    }



}
