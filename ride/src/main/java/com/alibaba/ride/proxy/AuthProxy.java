package com.alibaba.ride.proxy;

import com.alibaba.ride.model.dto.CreateAuthUserReqDTO;
import com.alibaba.ride.model.dto.IsExistsRespDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "AUTH-SERVER", url = "http://localhost:8080/author")
public interface AuthProxy {
    @GetMapping("/api/user/is-exists/{username}")
    ResponseEntity<IsExistsRespDTO> checkIfUserExist(@PathVariable("username") String username);
    @PostMapping("/api/user")
    ResponseEntity<HttpStatus> create(@RequestBody CreateAuthUserReqDTO dto, @RequestHeader("role") String role);
}
