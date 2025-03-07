package com.alibaba.drivermanagement.proxy;

import com.alibaba.drivermanagement.model.dto.CreateAuthUserReqDTO;
import com.alibaba.drivermanagement.model.dto.IsExistsRespDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "AUTH-SERVER", url = "http://localhost:8080")
public interface AuthProxy {
    @GetMapping("/api/user/is-exists/{username}")
    ResponseEntity<IsExistsRespDTO> checkIfUserExist(@PathVariable("username") String username);

    @PostMapping("/api/user")
    ResponseEntity<HttpStatus> create(@RequestBody CreateAuthUserReqDTO dto, @RequestHeader("role") String role);
}
