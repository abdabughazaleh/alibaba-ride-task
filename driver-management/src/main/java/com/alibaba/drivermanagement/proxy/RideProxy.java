package com.alibaba.drivermanagement.proxy;

import com.alibaba.drivermanagement.model.dto.RideDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;

@FeignClient(name = "RIDE-SERVER", url = "http://localhost:8085/ride-service")
public interface RideProxy {
    @GetMapping("/ride/driver-rides/{driver}")
    ResponseEntity<List<RideDTO>> getDriverRides(@PathVariable("driver") String driver,
                                                        @RequestParam("pageNumber") Integer pageNumber,
                                                        @RequestParam("pageSize") Integer pageSize);

}
