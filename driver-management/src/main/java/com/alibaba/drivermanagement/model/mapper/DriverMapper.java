package com.alibaba.drivermanagement.model.mapper;

import com.alibaba.drivermanagement.model.dto.DriverDTO;
import com.alibaba.drivermanagement.model.entity.Driver;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DriverMapper {
    DriverDTO toDTO(Driver driver);
}
