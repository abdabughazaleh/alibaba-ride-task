package com.alibaba.drivermanagement.service.impl;

import com.alibaba.drivermanagement.advice.ErrorHandler;
import com.alibaba.drivermanagement.model.dto.*;
import com.alibaba.drivermanagement.service.RideRequestProducerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RideRequestProducerImpl implements RideRequestProducerService {
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;
    @Value("${rabbitmq.routing.accept-key}")
    private String acceptKey;
    @Value("${rabbitmq.routing.complete-key}")
    private String completedKey;

    @Override
    @Retryable(
            value = {AmqpException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000, multiplier = 2)
    )
    public AcceptRideResponse sendAcceptRideEvent(AcceptRideRequest request) {
        log.info("send new ride.accepted event {} ", request);
        Object response;
        try {
            response = rabbitTemplate.convertSendAndReceive(exchange, acceptKey, request);

            if (response == null) {
                throw new ErrorHandler.CustomBadRequest(ErrorHandler.SystemErrors.SOMETHING_WRONG_IN_RIDE_ACCEPTED);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorHandler.CustomBadRequest(ErrorHandler.SystemErrors.SOMETHING_WRONG_IN_RIDE_ACCEPTED);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        AcceptRideResponse acceptRideResponse = objectMapper.convertValue(response, AcceptRideResponse.class);
        return acceptRideResponse;
    }

    @Override
    @Retryable(
            value = {AmqpException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000, multiplier = 2)
    )
    public CompleteRideResponse sendCompleteRideEvent(DriverCompleteRideReqDTO request) {
        log.info("send new ride.completed event {} ", request);
        Object response = rabbitTemplate.convertSendAndReceive(exchange, completedKey, request);
        if (response == null) {
            throw new ErrorHandler.CustomBadRequest(ErrorHandler.SystemErrors.SOMETHING_WRONG_IN_RIDE_COMPLETED);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        CompleteRideResponse completedRideResponse = objectMapper.convertValue(response, CompleteRideResponse.class);
        return completedRideResponse;
    }
}
