package com.MyRH.Models.Mappers;

import com.MyRH.Models.DTOs.ApplicationDto;
import com.MyRH.Models.Entities.Application;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface ApplicationMapper {

    ApplicationMapper INSTANCE = Mappers.getMapper(ApplicationMapper.class);

    Application toEntity(ApplicationDto ApplicationDto);

    ApplicationDto toDto(Application Application);
}