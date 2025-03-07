package com.alibaba.ride.service;

import com.alibaba.ride.model.dto.AcceptRideRequest;
import com.alibaba.ride.model.dto.CompleteRideReqDTO;
import com.alibaba.ride.model.dto.RideDTO;
import org.springframework.stereotype.Service;

@Service
public interface RabbitMQConsumerService {
    Object acceptRideEvent(AcceptRideRequest request);
    Object completeRideEvent(CompleteRideReqDTO dto);
}
