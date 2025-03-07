package com.alibaba.ride.model.dto;

import jakarta.validation.constraints.*;

import java.io.Serializable;

public record CreateRiderDTO(
        @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Name cannot contain numbers")
        String name,
        @Pattern(regexp = "^\\+966\\d{9}$", message = "Mobile number must start with +966 followed by 9 digits")
        String mobileNo,
        @NotNull(message = "Username cannot be null")
        String username,
        @Size(min = 7, message = "Password must be more than 7 characters")
        String password) implements Serializable {
}

