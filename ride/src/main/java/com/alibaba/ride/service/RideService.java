package com.alibaba.ride.service;

import com.alibaba.ride.model.dto.*;
import com.alibaba.ride.model.enums.RideStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RideService {
    PlaceRideRespDTO newRide(PlaceRideDTO dto);

    List<RideDTO> getRideByStatus(List<RideStatus> status);

    CancelRideRespDTO cancelRide(String tranNo);

    RideDTO acceptRide(String tranNo, String driver, String driverMobileNo);

    RideDTO completeRide(String tranNo);

    List<RideDTO> getDriverRides(String driver, Integer pageNumber, Integer pageSize);
}
