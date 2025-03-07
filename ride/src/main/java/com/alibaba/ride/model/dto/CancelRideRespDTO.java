package com.alibaba.ride.model.dto;

import com.alibaba.ride.model.enums.RideStatus;

public record CancelRideRespDTO(RideStatus status, String tranNo) {
}
