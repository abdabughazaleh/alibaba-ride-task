package com.alibaba.drivermanagement.service;

import com.alibaba.drivermanagement.model.dto.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public interface RideService {
    AcceptRideResponse acceptRide(DriverAcceptRideReqDTO request);

    CompleteRideResponse completeRide(DriverCompleteRideReqDTO dto);

    List<RideDTO> getDriverRides(Integer pageNumber, Integer pageSize);
}
