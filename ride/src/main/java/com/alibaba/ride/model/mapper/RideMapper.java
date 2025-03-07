package com.alibaba.ride.model.mapper;

import com.alibaba.ride.model.dto.RideDTO;
import com.alibaba.ride.model.entity.Ride;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RideMapper {
    List<RideDTO> toDTOs(List<Ride> rides);
    RideDTO toDTO(Ride ride);
}
