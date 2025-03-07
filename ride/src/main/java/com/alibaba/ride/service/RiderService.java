package com.alibaba.ride.service;

import com.alibaba.ride.model.dto.CreateRiderDTO;
import com.alibaba.ride.model.dto.CreateRiderRespDTO;
import com.alibaba.ride.model.dto.RiderDTO;
import org.springframework.stereotype.Service;

@Service
public interface RiderService {
    CreateRiderRespDTO createRider(CreateRiderDTO dto);
    RiderDTO getRiderByUsername(String username);
}
