package com.alibaba.authserver.controller;

import com.alibaba.authserver.model.dto.AuthReqDTO;
import com.alibaba.authserver.model.dto.AuthRespDTO;
import com.alibaba.authserver.model.dto.CreateUserReqDTO;
import com.alibaba.authserver.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthRespDTO> login(@RequestBody AuthReqDTO authReq) {
        return ResponseEntity.ok(userService.auth(authReq));
    }
}
