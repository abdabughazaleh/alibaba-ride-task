package com.alibaba.drivermanagement.controller;

import com.alibaba.drivermanagement.model.dto.*;
import com.alibaba.drivermanagement.model.enums.TripNotificationStatus;
import com.alibaba.drivermanagement.service.DriverService;
import com.alibaba.drivermanagement.service.TripNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationsController {
    private final TripNotificationService tripNotificationService;

    @GetMapping
    public ResponseEntity<List<TripNotificationDTO>> driverNotifications(@RequestParam(value = "status", required = false) List<TripNotificationStatus> status) {
        List<TripNotificationDTO> notifications;
        if (status == null || status.isEmpty())
            notifications = this.tripNotificationService.findAllByDriver();
        else
            notifications = this.tripNotificationService.findAllByDriverAndStatus(status);
        return ResponseEntity.ok(notifications);
    }

}
