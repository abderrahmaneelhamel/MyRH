package com.MyRH.Models.Mappers;

import com.MyRH.Models.DTOs.AdminDto;
import com.MyRH.Models.Entities.Admin;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper(componentModel = "spring")
public interface AdminMapper {

    AdminMapper INSTANCE = Mappers.getMapper(AdminMapper.class);

    AdminDto toDto(Admin Admin);

    Admin toEntity(AdminDto AdminDto);
}