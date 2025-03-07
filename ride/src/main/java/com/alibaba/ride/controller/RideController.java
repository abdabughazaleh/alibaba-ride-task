package com.alibaba.ride.controller;

import com.alibaba.ride.model.dto.*;
import com.alibaba.ride.model.enums.RideStatus;
import com.alibaba.ride.service.RideService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ride")
@RequiredArgsConstructor
public class RideController {
    private final RideService rideService;

    @PostMapping
    public ResponseEntity<PlaceRideRespDTO> placeRide(@RequestBody PlaceRideDTO dto) {
        return ResponseEntity.ok(this.rideService.newRide(dto));
    }

    @GetMapping("/{status}")
    public ResponseEntity<List<RideDTO>> getUserRides(@PathVariable("status") List<RideStatus> stats) {
        List<RideDTO> rideRespDTOS = this.rideService.getRideByStatus(stats);
        return ResponseEntity.ok(rideRespDTOS);
    }

    @GetMapping("/driver-rides/{driver}")
    public ResponseEntity<List<RideDTO>> getDriverRides(@PathVariable("driver") String driver,
                                                        @RequestParam("pageNumber") Integer pageNumber,
                                                        @RequestParam("pageSize") Integer pageSize) {
        List<RideDTO> rideRespDTOS = this.rideService.getDriverRides(driver, pageNumber, pageSize);
        return ResponseEntity.ok(rideRespDTOS);
    }

    @PutMapping("/cancel/{tran_no}")
    public ResponseEntity<CancelRideRespDTO> cancelRide(@PathVariable("tran_no") String tranNo) {
        CancelRideRespDTO rideRespDTOS = this.rideService.cancelRide(tranNo);
        return ResponseEntity.ok(rideRespDTOS);
    }
}
