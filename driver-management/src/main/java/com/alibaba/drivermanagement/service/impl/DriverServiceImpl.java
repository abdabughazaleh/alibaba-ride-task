package com.alibaba.drivermanagement.service.impl;

import com.alibaba.drivermanagement.advice.ErrorHandler;
import com.alibaba.drivermanagement.model.dto.*;
import com.alibaba.drivermanagement.model.entity.Driver;
import com.alibaba.drivermanagement.model.enums.DriverStatus;
import com.alibaba.drivermanagement.model.enums.Roles;
import com.alibaba.drivermanagement.model.mapper.DriverMapper;
import com.alibaba.drivermanagement.model.repository.DriverRepository;
import com.alibaba.drivermanagement.proxy.AuthProxy;
import com.alibaba.drivermanagement.service.DriverService;
import com.alibaba.drivermanagement.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {
    private final DriverRepository driverRepository;
    private final JwtUtil jwtUtil;
    private final AuthProxy authProxy;
    private final DriverMapper driverMapper;

    @Override
    public CreateDriverRespDTO createDriver(CreateDriverReqDTO reqDTO) {
        log.info("DriverService::createDriver dto {}", reqDTO);
        validateIfUserExistInAuthServer(reqDTO.username());
        validateIfDriverExist(reqDTO.mobileNo());
        createUserAuth(reqDTO.username(), reqDTO.password());
        Driver driverEntity = Driver.builder()
                .name(reqDTO.name())
                .mobileNo(reqDTO.mobileNo())
                .username(reqDTO.username())
                .licenseNumber(reqDTO.licenseNumber())
                .build();
        log.info("DriverService::createDriver driverEntity {}", driverEntity);
        Driver savedEntity = this.driverRepository.save(driverEntity);
        log.info("DriverService::createDriver savedEntity {}", driverEntity);
        return new CreateDriverRespDTO(savedEntity.getName(),
                savedEntity.getUsername(),
                savedEntity.getLicenseNumber(),
                savedEntity.getMobileNo());
    }

    @Override
    public UpdateDriverStatusRespDTO updateDriverStatus(UpdateDriverStatusReqDTO reqDTO) {
        String username = this.jwtUtil.extractUsername();
        Optional<Driver> driver = this.driverRepository.findByUsername(username);
        if (driver.isEmpty()) {
            throw new ErrorHandler.CustomBadRequest(ErrorHandler.SystemErrors.DRIVER_NOT_EXISTS);
        }
        Driver newDriverEntity = driver.get();
        newDriverEntity.setStatus(reqDTO.status());
        Driver saveEntity = this.driverRepository.save(newDriverEntity);
        return new UpdateDriverStatusRespDTO(saveEntity.getStatus());
    }

    @Override
    public List<DriverDTO> getDriversByStatus(DriverStatus status) {
        List<Driver> drivers = this.driverRepository.findAllByStatus(status);
        return drivers.stream().map(driver -> DriverDTO.builder()
                .status(driver.getStatus())
                .rating(driver.getRating())
                .name(driver.getName())
                .mobileNo(driver.getMobileNo())
                .licenseNumber(driver.getLicenseNumber())
                .username(driver.getUsername())
                .build()).toList();
    }

    @Override
    public DriverDTO getDriverDetailsByUsername(String username) {
        Optional<Driver> driver = this.driverRepository.findByUsername(username);
        if (driver.isEmpty()) {
            throw new ErrorHandler.CustomBadRequest(ErrorHandler.SystemErrors.DRIVER_NOT_EXISTS);
        }
        return this.driverMapper.toDTO(driver.get());
    }

    private void validateIfDriverExist(String mobileNo) {
        Optional<Driver> driverO = this.driverRepository.findByMobileNo(mobileNo);
        if (driverO.isPresent()) {
            log.info("DriverService::createDriver the driver with this mobile {} is exist registered before", mobileNo);
            throw new ErrorHandler.CustomBadRequest(ErrorHandler.SystemErrors.DRIVER_ALREADY_EXISTS);
        }
    }

    /*
     * This method do main things
     * 1 - Check if user exist before create user in Auth Server!
     * 2 - And check Auth-Server service health (by design patter categories "Scatter Gather or Two Phase Commits")
     * */
    private void validateIfUserExistInAuthServer(String username) {
        try {
            log.info("DriverService::validateIfUserExistInAuthServer username {}", username);
            ResponseEntity<IsExistsRespDTO> userExist = this.authProxy.checkIfUserExist(username);
            log.info("DriverService::validateIfUserExistInAuthServer userExist {}", userExist);
            if (userExist.getBody() != null) {
                log.info("DriverService::validateIfUserExistInAuthServer user exist with http status status {}", userExist.getStatusCode());
                log.info("DriverService::validateIfUserExistInAuthServer userExist {}", userExist.getBody());
                if (userExist.getBody().status()) {
                    throw new ErrorHandler.CustomBadRequest(ErrorHandler.SystemErrors.DRIVER_ALREADY_EXISTS);
                }
            } else {
                throw new ErrorHandler.CustomBadRequest(ErrorHandler.SystemErrors.GENERAL_ERROR);
            }
        } catch (Exception e) {
            log.error("DriverService::validateIfUserExistInAuthServer error while validate user {} error {} ", username, e.getMessage());
            throw new ErrorHandler.CustomBadRequest(ErrorHandler.SystemErrors.GENERAL_ERROR);
        }
    }

    private void createUserAuth(String username, String password) {
        try {
            ResponseEntity<HttpStatus> userCreated = this.authProxy.create(new CreateAuthUserReqDTO(username, password), Roles.DRIVER.name());
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
