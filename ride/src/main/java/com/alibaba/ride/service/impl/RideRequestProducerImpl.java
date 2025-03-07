package com.alibaba.ride.service.impl;

import com.alibaba.ride.model.dto.AcceptRideRequest;
import com.alibaba.ride.model.dto.RequestCancelDTO;
import com.alibaba.ride.model.dto.RequestRideDTO;
import com.alibaba.ride.service.RideRequestProducerService;
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

    @Value("${rabbitmq.routing.request-key}")
    private String requestKey;

    @Value("${rabbitmq.routing.cancel-key}")
    private String cancelKey;

    @Override
    @Retryable(
            value = {AmqpException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000, multiplier = 2)
    )
    public void sendRideRequest(RequestRideDTO request) {
        log.info("send new ride.requested {} ", request);
        rabbitTemplate.convertAndSend(exchange, requestKey, request);
    }

    @Override
    @Retryable(
            value = {AmqpException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000, multiplier = 2)
    )
    public void sendCancelRequest(RequestCancelDTO request) {
        log.info("send new ride.canceled {} ", request);
        rabbitTemplate.convertAndSend(exchange, cancelKey, request);
    }

}
