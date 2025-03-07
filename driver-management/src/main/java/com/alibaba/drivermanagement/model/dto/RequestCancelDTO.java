package com.alibaba.drivermanagement.model.dto;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record RequestCancelDTO(String transactionNo) implements Serializable {
}