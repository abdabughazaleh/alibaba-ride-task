package com.alibaba.authserver.controller;

import com.alibaba.authserver.model.dto.AuthReqDTO;
import com.alibaba.authserver.model.dto.AuthRespDTO;
import com.alibaba.authserver.model.dto.CreateUserReqDTO;
import com.alibaba.authserver.model.dto.IsExistsRespDTO;
import com.alibaba.authserver.model.enums.Roles;
import com.alibaba.authserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody CreateUserReqDTO dto, @RequestHeader("role") Roles role) {
        userService.create(dto, role);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/is-exists/{username}")
    public ResponseEntity<IsExistsRespDTO> checkIfUserExist(@PathVariable("username") String username) {
        return ResponseEntity.ok(this.userService.checkIfUserExist(username));
    }
}
