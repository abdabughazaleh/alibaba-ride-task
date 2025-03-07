package com.alibaba.ride.model.enums;

import java.io.Serializable;

public enum RideStatus implements Serializable {
    REQUESTED, COMPLETED, IN_PROGRESS, PAYMENT_FAILED, CANCELED
}
