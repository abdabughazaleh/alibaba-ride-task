package com.alibaba.ride.service.impl;

import com.alibaba.ride.advice.ErrorHandler;
import com.alibaba.ride.model.dto.AcceptRideRequest;
import com.alibaba.ride.model.dto.CompleteRideReqDTO;
import com.alibaba.ride.model.dto.RideDTO;
import com.alibaba.ride.service.RabbitMQConsumerService;
import com.alibaba.ride.service.RideService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RabbitMQConsumerImpl implements RabbitMQConsumerService {
    private final RideService rideService;

    @RabbitListener(queues = "${rabbitmq.queues.accept}")
    @Retryable(value = ErrorHandler.CustomBadRequest.class, maxAttempts = 3, backoff = @Backoff(delay = 2000))
    public Object acceptRideEvent(AcceptRideRequest dto) {
        log.info("new ride.requested event {}", dto);
        return this.rideService.acceptRide(dto.transactionNo(), dto.driver(), dto.driverMobileNo());
    }


    @Override
    @RabbitListener(queues = "${rabbitmq.queues.complete}")
    @Retryable(value = ErrorHandler.CustomBadRequest.class, maxAttempts = 3, backoff = @Backoff(delay = 2000))
    public Object completeRideEvent(CompleteRideReqDTO dto) {
        log.info("new ride.completed event {}", dto);
        return this.rideService.completeRide(dto.tranNo());
    }
}