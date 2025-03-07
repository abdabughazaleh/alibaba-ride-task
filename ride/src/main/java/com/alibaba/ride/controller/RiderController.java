package com.alibaba.ride.controller;

import com.alibaba.ride.model.dto.CreateRiderDTO;
import com.alibaba.ride.model.dto.CreateRiderRespDTO;
import com.alibaba.ride.service.RiderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rider")
@RequiredArgsConstructor
public class RiderController {
    private final RiderService riderService;
    @PostMapping("/signup")
    public ResponseEntity<CreateRiderRespDTO> signUp(@RequestBody CreateRiderDTO dto) {
        CreateRiderRespDTO rider = this.riderService.createRider(dto);
        return ResponseEntity.ok(rider);
    }
}
