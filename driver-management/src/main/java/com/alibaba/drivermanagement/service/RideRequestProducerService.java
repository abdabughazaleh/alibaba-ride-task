package com.alibaba.drivermanagement.service;


import com.alibaba.drivermanagement.model.dto.AcceptRideRequest;
import com.alibaba.drivermanagement.model.dto.AcceptRideResponse;
import com.alibaba.drivermanagement.model.dto.CompleteRideResponse;
import com.alibaba.drivermanagement.model.dto.DriverCompleteRideReqDTO;
import org.springframework.stereotype.Service;

@Service
public interface RideRequestProducerService {
    AcceptRideResponse sendAcceptRideEvent(AcceptRideRequest request);
    CompleteRideResponse sendCompleteRideEvent(DriverCompleteRideReqDTO request);
}
