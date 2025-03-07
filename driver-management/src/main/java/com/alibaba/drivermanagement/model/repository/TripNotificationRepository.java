package com.alibaba.drivermanagement.model.repository;

import com.alibaba.drivermanagement.model.entity.TripNotification;
import com.alibaba.drivermanagement.model.enums.TripNotificationStatus;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TripNotificationRepository extends JpaRepository<TripNotification, Long> {
    @Modifying
    @Query("UPDATE TripNotification u SET u.status = :status WHERE u.tripTranNo = :tranNo")
    void updateStatusByTripTransNo(@Param("tranNo") String tranNo, @Param("status") TripNotificationStatus status);

    List<TripNotification> findAllByTripTranNo(String tranNo);

    List<TripNotification> findAllByDriver(String driver);

    List<TripNotification> findAllByDriverAndStatusIn(String driver, List<TripNotificationStatus> status);
}
