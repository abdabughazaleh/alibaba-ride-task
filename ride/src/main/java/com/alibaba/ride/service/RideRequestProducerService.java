package com.alibaba.ride.service;


import com.alibaba.ride.model.dto.AcceptRideRequest;
import com.alibaba.ride.model.dto.RequestCancelDTO;
import com.alibaba.ride.model.dto.RequestRideDTO;
import com.alibaba.ride.model.dto.TripRespDTO;
import org.springframework.stereotype.Service;

@Service
public interface RideRequestProducerService {
    void sendRideRequest(RequestRideDTO request);

    void sendCancelRequest(RequestCancelDTO request);
}
