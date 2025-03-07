package com.alibaba.ride.model.repository;

import com.alibaba.ride.model.entity.Ride;
import com.alibaba.ride.model.enums.RideStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RideRepository extends JpaRepository<Ride, Long> {
    List<Ride> findAllByUsernameAndStatusIn(String username, List<RideStatus> status);

    Optional<Ride> findByTransactionNoAndUsername(String tranNo, String username);

    Optional<Ride> findByTransactionNo(String tranNo);
}
