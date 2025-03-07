package com.alibaba.ride.service.impl;

import com.alibaba.ride.advice.ErrorHandler;
import com.alibaba.ride.model.dto.*;
import com.alibaba.ride.model.entity.Rider;
import com.alibaba.ride.model.enums.Roles;
import com.alibaba.ride.model.repository.RiderRepository;
import com.alibaba.ride.proxy.AuthProxy;
import com.alibaba.ride.service.RiderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class RiderServiceImpl implements RiderService {
    private final RiderRepository riderRepository;
    private final AuthProxy authProxy;

    @Override
    public CreateRiderRespDTO createRider(CreateRiderDTO dto) {
        log.info("RiderService::createRider dto {}", dto);
        validateIfUserExistInAuthServer(dto.username());
        validateIfDriverExist(dto);
        Rider rider = Rider.builder()
                .name(dto.name())
                .username(dto.username())
                .mobileNo(dto.mobileNo())
                .build();
        createUserAuth(dto.username(), dto.password());
        Rider savedRider = this.riderRepository.save(rider);
        return new CreateRiderRespDTO(savedRider.getName(), savedRider.getUsername(), savedRider.getMobileNo());
    }

    private void validateIfDriverExist(CreateRiderDTO dto) {
        Optional<Rider> oRider = this.riderRepository.findByUsernameOrMobileNo(dto.username(), dto.mobileNo());
        if (oRider.isPresent()) {
            throw new ErrorHandler.CustomBadRequest(ErrorHandler.SystemErrors.RIDER_ALREADY_EXISTS);
        }
    }

    @Override
    public RiderDTO getRiderByUsername(String username) {
        Optional<Rider> oRider = this.riderRepository.findByUsernameOrMobileNo(username, null);
        if (oRider.isEmpty()) {
            throw new ErrorHandler.CustomBadRequest(ErrorHandler.SystemErrors.RIDER_NOT_EXISTS);
        }
        return new RiderDTO(oRider.get().getName(), oRider.get().getUsername(), oRider.get().getMobileNo());
    }

    /*
     * This method do main things
     * 1 - Check if user exist before create user in Auth Server!
     * 2 - And check Auth-Server service health (by design patter categories "Scatter Gather or Two Phase Commits")
     * */
    private void validateIfUserExistInAuthServer(String username) {
        log.info("RiderService::validateIfUserExistInAuthServer username {}", username);
        ResponseEntity<IsExistsRespDTO> userExist;
        try {
            userExist = this.authProxy.checkIfUserExist(username);
        } catch (Exception e) {
            log.error("RiderService::validateIfUserExistInAuthServer error while validate user {} error {} ", username, e.getMessage());
            throw new ErrorHandler.CustomBadRequest(ErrorHandler.SystemErrors.GENERAL_ERROR);
        }
        log.info("RiderService::validateIfUserExistInAuthServer userExist {}", userExist);
        if (userExist.getBody() != null) {
            log.info("RiderService::validateIfUserExistInAuthServer user exist with http status status {}", userExist.getStatusCode());
            log.info("RiderService::validateIfUserExistInAuthServer userExist {}", userExist.getBody());
            if (userExist.getBody().status()) {
                throw new ErrorHandler.CustomBadRequest(ErrorHandler.SystemErrors.USER_ALREADY_EXISTS);
            }
        } else {
            throw new ErrorHandler.CustomBadRequest(ErrorHandler.SystemErrors.GENERAL_ERROR);
        }

    }


    private void createUserAuth(String username, String password) {
        try {
            ResponseEntity<HttpStatus> userCreated = this.authProxy.create(new CreateAuthUserReqDTO(username, password), Roles.RIDER.name());
            if (userCreated.getBody() != null) {
                throw new ErrorHandler.CustomBadRequest(ErrorHandler.SystemErrors.GENERAL_ERROR);
            }
            if (!userCreated.getStatusCode().is2xxSuccessful()) {
                throw new ErrorHandler.CustomBadRequest(ErrorHandler.SystemErrors.GENERAL_ERROR);
            }
        } catch (Exception e) {
            throw new ErrorHandler.CustomBadRequest(ErrorHandler.SystemErrors.GENERAL_ERROR);
        }
    }
}
