package com.alibaba.authserver.service;

import com.alibaba.authserver.model.dto.AuthReqDTO;
import com.alibaba.authserver.model.dto.AuthRespDTO;
import com.alibaba.authserver.model.dto.CreateUserReqDTO;
import com.alibaba.authserver.model.dto.IsExistsRespDTO;
import com.alibaba.authserver.model.entity.User;
import com.alibaba.authserver.model.enums.Roles;
import com.alibaba.authserver.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {

    @Mock
    UserService userService;

    @Mock
    UserRepository userRepository;

    @BeforeEach
    void init() {
        when(this.userRepository.findByUsername("admin")).thenReturn(Optional.ofNullable(User.builder()
                .role(Roles.ADMIN)
                .password("aaaaaaaaaaaaaa")
                .username("admin")
                .id(1L)
                .build()));
        when(this.userRepository.save(User.builder()
                .role(Roles.ADMIN)
                .password("aaaaaaaaaaaaaa")
                .username("admin")
                .build())).thenReturn(User.builder()
                .role(Roles.ADMIN)
                .password("admin")
                .username("admin")
                .id(10L)
                .build());
        when(this.userService.auth(new AuthReqDTO("admin", "admin")))
                .thenReturn(new AuthRespDTO("demo_token"));
        when(this.userService.checkIfUserExist("admin"))
                .thenReturn(new IsExistsRespDTO(true));

    }

    @Test
    void auth() {
        AuthRespDTO user = this.userService.auth(new AuthReqDTO("admin", "admin"));
        assertNotNull(user.token());
    }

    @Test
    void create() {
        this.userService.create(new CreateUserReqDTO("admin" , "password"), Roles.ADMIN);
    }

    @Test
    void checkIfUserExist() {
        IsExistsRespDTO status = this.userService.checkIfUserExist("admin");
        assertTrue(status.status());
    }
}