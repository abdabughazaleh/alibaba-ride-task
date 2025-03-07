package com.alibaba.drivermanagement.service.impl;

import com.alibaba.drivermanagement.advice.ErrorHandler;
import com.alibaba.drivermanagement.model.dto.TripNotificationDTO;
import com.alibaba.drivermanagement.model.entity.TripNotification;
import com.alibaba.drivermanagement.model.enums.TripNotificationStatus;
import com.alibaba.drivermanagement.model.mapper.TripNotificationMapper;
import com.alibaba.drivermanagement.model.repository.TripNotificationRepository;
import com.alibaba.drivermanagement.service.TripNotificationService;
import com.alibaba.drivermanagement.util.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TripNotificationServiceImpl implements TripNotificationService {
    private final TripNotificationRepository tripNotificationRepository;
    private final JwtUtil jwtUtil;
    private final TripNotificationMapper tripNotificationMapper;
    @Override
    public void sendNotificationToNearDriver(List<TripNotification> notifications) {
        this.tripNotificationRepository.saveAll(notifications);
    }

    @Override
    @Transactional
    public void sendCancelNotification(String tranNo) {
        List<TripNotification> notifications = this.tripNotificationRepository.findAllByTripTranNo(tranNo);
        if (notifications.isEmpty()) {
            return;
        }
        this.tripNotificationRepository.updateStatusByTripTransNo(tranNo , TripNotificationStatus.CANCELED);
    }

    @Override
    public List<TripNotificationDTO> findAllByDriver() {
        List<TripNotification> notifications = this.tripNotificationRepository.findAllByDriver(jwtUtil.extractUsername());
        return this.tripNotificationMapper.toDTOs(notifications);
    }

    @Override
    public List<TripNotificationDTO> findAllByDriverAndStatus(List<TripNotificationStatus> status) {
        List<TripNotificationStatus> statusList = List.of(TripNotificationStatus.values());
        if (!new HashSet<>(statusList).containsAll(status)){
            throw new ErrorHandler.CustomBadRequest(ErrorHandler.SystemErrors.NOTIFICATION_STATUS_NOT_FOUND);
        }
        List<TripNotification> notifications = this.tripNotificationRepository.findAllByDriverAndStatusIn(jwtUtil.extractUsername(), status);
        return this.tripNotificationMapper.toDTOs(notifications);
    }
}
