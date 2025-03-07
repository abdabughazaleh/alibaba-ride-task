package com.alibaba.drivermanagement.service;

import com.alibaba.drivermanagement.model.dto.RequestCancelDTO;
import com.alibaba.drivermanagement.model.dto.RequestRideDTO;
import org.springframework.stereotype.Service;

@Service
public interface RabbitMQConsumerService {
    void receiveRequestEvent(RequestRideDTO payload);
    void receiveCancelEvent(RequestCancelDTO payload);
}
