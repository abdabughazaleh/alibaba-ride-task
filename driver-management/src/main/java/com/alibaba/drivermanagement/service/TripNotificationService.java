package com.alibaba.drivermanagement.service;


import com.alibaba.drivermanagement.model.dto.TripNotificationDTO;
import com.alibaba.drivermanagement.model.entity.TripNotification;
import com.alibaba.drivermanagement.model.enums.TripNotificationStatus;
import org.aspectj.weaver.ast.Not;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TripNotificationService {
    public void sendNotificationToNearDriver(List<TripNotification> notifications);

    public void sendCancelNotification(String tranNo);

    List<TripNotificationDTO> findAllByDriver();
    List<TripNotificationDTO> findAllByDriverAndStatus(List<TripNotificationStatus> status);
}
