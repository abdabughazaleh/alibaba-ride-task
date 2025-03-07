package com.alibaba.ride.model.repository;

import com.alibaba.ride.model.entity.Rider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RiderRepository extends JpaRepository<Rider, Long> {
    Optional<Rider> findByUsernameOrMobileNo(String username, String mobileNo);
}
