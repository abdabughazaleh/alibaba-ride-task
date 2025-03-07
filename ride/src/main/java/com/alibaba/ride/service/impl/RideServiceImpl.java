package com.alibaba.ride.service.impl;

import com.alibaba.ride.advice.ErrorHandler;
import com.alibaba.ride.model.dto.*;
import com.alibaba.ride.model.entity.Ride;
import com.alibaba.ride.model.enums.RideStatus;
import com.alibaba.ride.model.enums.TripStatus;
import com.alibaba.ride.model.mapper.RideMapper;
import com.alibaba.ride.model.repository.RideRepository;
import com.alibaba.ride.service.RideRequestProducerService;
import com.alibaba.ride.service.RideService;
import com.alibaba.ride.service.RiderService;
import com.alibaba.ride.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class RideServiceImpl implements RideService {
    private final RideRepository rideRepository;
    private final RiderService riderService;
    private final JwtUtil jwtUtil;
    private final RideRequestProducerService rideRequestProducerService;
    private final RideMapper rideMapper;

    @Override
    public PlaceRideRespDTO newRide(PlaceRideDTO dto) {
        if (dto.paymentMethod() == null) {
            throw new ErrorHandler.CustomBadRequest(ErrorHandler.SystemErrors.PAYMENT_METHOD_UNSELECTED);
        }
        String username = this.jwtUtil.extractUsername();
        RiderDTO rider = this.riderService.getRiderByUsername(username);
        validateIfUserHaveUnCompleteRide(username);
        Ride ride = Ride.builder()
                .name(rider.name())
                .amount(dto.amount())
                .dropAt(dto.dropAt())
                .mobileNo(rider.mobileNo())
                .locatedAt(dto.locatedAt())
                .status(RideStatus.REQUESTED)
                .username(rider.username())
                .build();
        Ride savedRide = this.rideRepository.save(ride);
        RequestRideDTO event = RequestRideDTO.builder()
                .amount(savedRide.getAmount())
                .dropAt(savedRide.getDropAt())
                .locatedAt(savedRide.getLocatedAt())
                .transactionNo(savedRide.getTransactionNo())
                .rider(savedRide.getUsername())
                .build();
        this.rideRequestProducerService.sendRideRequest(event);
        return new PlaceRideRespDTO(savedRide.getLocatedAt(),
                savedRide.getDropAt(),
                savedRide.getAmount(),
                savedRide.getTransactionNo());
    }

    @Override
    public List<RideDTO> getRideByStatus(List<RideStatus> stats) {
        List<Ride> rides = this.rideRepository.findAllByUsernameAndStatusIn(jwtUtil.extractUsername(), stats);
        List<RideDTO> dtOs = this.rideMapper.toDTOs(rides);
        log.info("getting ride {}", dtOs);
        return dtOs;
    }

    @Override
    public CancelRideRespDTO cancelRide(String tranNo) {
        String s = jwtUtil.extractUsername();
        Optional<Ride> ride = this.rideRepository.findByTransactionNoAndUsername(tranNo, jwtUtil.extractUsername());
        ride.orElseThrow(() -> new ErrorHandler.CustomBadRequest(ErrorHandler.SystemErrors.TRANSACTION_NOT_FOUND));
        validateRideStatus(ride.get().getStatus());
        ride.get().setStatus(RideStatus.CANCELED);
        this.rideRepository.save(ride.get());
        this.rideRequestProducerService.sendCancelRequest(new RequestCancelDTO(tranNo));
        return new CancelRideRespDTO(RideStatus.CANCELED, tranNo);
    }

    @Override
    public RideDTO acceptRide(String tranNo, String driver, String driverMobileNo) {
        Optional<Ride> transaction = this.rideRepository.findByTransactionNo(tranNo);
        transaction.orElseThrow(() -> new ErrorHandler.CustomBadRequest(ErrorHandler.SystemErrors.TRANSACTION_NOT_FOUND));
        if (transaction.get().getStatus() != RideStatus.REQUESTED) {
            // throw new ErrorHandler.CustomBadRequest(ErrorHandler.SystemErrors.YOU_CANNOT_ACCEPT_THIS_RIDE);
            return new RideDTO();
        }
        transaction.get().setStatus(RideStatus.IN_PROGRESS);
        transaction.get().setDriver(driver);
        transaction.get().setDriverMobileNo(driverMobileNo);

        Ride savedRide = this.rideRepository.save(transaction.get());
        return this.rideMapper.toDTO(savedRide);
    }

    @Override
    public RideDTO completeRide(String tranNo) {
        Optional<Ride> ride = this.rideRepository.findByTransactionNo(tranNo);
        if (ride.isEmpty()) {
            return null;
        }
        ride.get().setStatus(RideStatus.COMPLETED);
        Ride save = this.rideRepository.save(ride.get());
        return this.rideMapper.toDTO(save);
    }

    @Override
    public List<RideDTO> getDriverRides(String driver, Integer pageNumber, Integer pageSize) {
        List<Ride> rides = this.rideRepository.findByDriverOrderByIdDesc(driver, PageRequest.of(pageNumber, pageSize));
        return this.rideMapper.toDTOs(rides);
    }

    private void validateRideStatus(RideStatus status) {
        if (!status.equals(RideStatus.REQUESTED))
            throw new ErrorHandler.CustomBadRequest(ErrorHandler.SystemErrors.CANNOT_CANCEL_THIS_RIDE);
    }

    private void validateIfUserHaveUnCompleteRide(String username) {
        List<Ride> rides = this.rideRepository.findAllByUsernameAndStatusIn(username, List.of(RideStatus.IN_PROGRESS, RideStatus.REQUESTED));
        if (!rides.isEmpty()) {
            throw new ErrorHandler.CustomBadRequest(ErrorHandler.SystemErrors.UNCOMPLETED_RIDE);
        }
    }
}
