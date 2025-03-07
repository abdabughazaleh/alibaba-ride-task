package com.alibaba.drivermanagement.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public record CreateDriverReqDTO(
        @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Name cannot contain numbers")
        String name,
        @NotNull(message = "Username cannot be null")
        String username,
        @Size(min = 7, message = "Password must be more than 7 characters")
        String password,
        @Pattern(regexp = "^[A-Za-z0-9]+$", message = "License number must be alphanumeric")
        String licenseNumber,
        @Pattern(regexp = "^\\+966\\d{9}$", message = "Mobile number must start with +966 followed by 9 digits")
        String mobileNo
) implements Serializable {
}
