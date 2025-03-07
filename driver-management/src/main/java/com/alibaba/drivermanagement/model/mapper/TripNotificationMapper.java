package com.alibaba.drivermanagement.model.mapper;

import com.alibaba.drivermanagement.model.dto.TripNotificationDTO;
import com.alibaba.drivermanagement.model.entity.TripNotification;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TripNotificationMapper {
    List<TripNotificationDTO> toDTOs(List<TripNotification> entities);
}
