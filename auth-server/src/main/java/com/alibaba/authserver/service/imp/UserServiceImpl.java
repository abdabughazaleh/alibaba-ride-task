package com.alibaba.authserver.service.imp;

import com.alibaba.authserver.config.JwtService;
import com.alibaba.authserver.model.dto.AuthReqDTO;
import com.alibaba.authserver.model.dto.AuthRespDTO;
import com.alibaba.authserver.model.dto.CreateUserReqDTO;
import com.alibaba.authserver.model.dto.IsExistsRespDTO;
import com.alibaba.authserver.model.entity.User;
import com.alibaba.authserver.model.enums.Roles;
import com.alibaba.authserver.repository.UserRepository;
import com.alibaba.authserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public AuthRespDTO auth(AuthReqDTO reqDTO) {
        Optional<User> user = this.userRepository.findByUsername(reqDTO.username());
        if (user.isEmpty())
            throw new RuntimeException("Username or password not valid");
        String password = user.get().getPassword();
        boolean matches = passwordEncoder.matches(reqDTO.password(), password);
        if (!matches)
            throw new RuntimeException("Username or password not valid");
        return new AuthRespDTO(jwtService.generateToken(user.get().getUsername()));
    }

    @Override
    public void create(CreateUserReqDTO dto, Roles role) {
        Optional<User> user = this.userRepository.findByUsername(dto.username());
        if (user.isPresent()) {
            throw new RuntimeException("User already exist");
        }
        PasswordEncoder passwordEncoder1 = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder1.encode(dto.password());
        User userEntity = User.builder()
                .username(dto.username())
                .password(encodedPassword)
                .role(role)
                .build();
        this.userRepository.save(userEntity);
    }

    @Override
    public IsExistsRespDTO checkIfUserExist(String username) {
        Optional<User> user = this.userRepository.findByUsername(username);
        return new IsExistsRespDTO(user.isPresent());
    }
}
