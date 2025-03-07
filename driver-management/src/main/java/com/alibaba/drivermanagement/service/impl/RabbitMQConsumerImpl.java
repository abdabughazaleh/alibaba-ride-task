package com.alibaba.drivermanagement.service.impl;


import com.alibaba.drivermanagement.advice.ErrorHandler;
import com.alibaba.drivermanagement.model.dto.RequestCancelDTO;
import com.alibaba.drivermanagement.model.dto.RequestRideDTO;
import com.alibaba.drivermanagement.model.entity.TripNotification;
import com.alibaba.drivermanagement.model.enums.DriverStatus;
import com.alibaba.drivermanagement.model.enums.TripNotificationStatus;
import com.alibaba.drivermanagement.service.DriverService;
import com.alibaba.drivermanagement.service.RabbitMQConsumerService;
import com.alibaba.drivermanagement.service.TripNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RabbitMQConsumerImpl implements RabbitMQConsumerService {
    private final DriverService driverService;
    private final TripNotificationService tripNotificationService;

    @RabbitListener(queues = "${rabbitmq.queues.request-ride}")
    @Retryable(value = ErrorHandler.CustomBadRequest.class, maxAttempts = 3, backoff = @Backoff(delay = 2000))
    public void receiveRequestEvent(RequestRideDTO dto) {
        log.info("new ride.requested event {}", dto);
        List<TripNotification> notificationsList = this.driverService.getDriversByStatus(DriverStatus.ONLINE)
                .stream().map(driver -> TripNotification.builder()
                        .dropAt(dto.dropAt())
                        .locatedAt(dto.locatedAt())
                        .status(TripNotificationStatus.REQUESTED)
                        .driver(driver.getUsername())
                        .amount(dto.amount())
                        .tripTranNo(dto.transactionNo())
                        .build()).toList();
        // suppose these are near drivers.....
        this.tripNotificationService.sendNotificationToNearDriver(notificationsList);
    }
    @RabbitListener(queues = "${rabbitmq.queues.cancel-ride}")
    @Retryable(value = ErrorHandler.CustomBadRequest.class, maxAttempts = 3, backoff = @Backoff(delay = 2000))
    public void receiveCancelEvent(RequestCancelDTO dto) {
        log.info("new ride.canceled event {}", dto);
        this.tripNotificationService.sendCancelNotification(dto.transactionNo());
    }
}