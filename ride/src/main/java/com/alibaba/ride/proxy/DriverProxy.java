package com.alibaba.ride.proxy;

import com.alibaba.ride.model.dto.CreateAuthUserReqDTO;
import com.alibaba.ride.model.dto.DriverDTO;
import com.alibaba.ride.model.dto.IsExistsRespDTO;
import com.alibaba.ride.model.dto.SubmitRateDTO;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "DRIVER-SERVER", url = "http://driver-service:8081/driver-service")
public interface DriverProxy {
    @GetMapping("/driver/rate")
    ResponseEntity<DriverDTO> submitDriverRate(@RequestBody SubmitRateDTO reqDTO);
}
