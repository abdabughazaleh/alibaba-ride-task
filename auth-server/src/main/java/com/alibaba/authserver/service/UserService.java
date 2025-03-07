package com.alibaba.authserver.service;

import com.alibaba.authserver.model.dto.AuthReqDTO;
import com.alibaba.authserver.model.dto.AuthRespDTO;
import com.alibaba.authserver.model.dto.CreateUserReqDTO;
import com.alibaba.authserver.model.dto.IsExistsRespDTO;
import com.alibaba.authserver.model.enums.Roles;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    public AuthRespDTO auth(AuthReqDTO reqDTO);

    void create(CreateUserReqDTO dto, Roles role);

    IsExistsRespDTO checkIfUserExist(String username);
}
