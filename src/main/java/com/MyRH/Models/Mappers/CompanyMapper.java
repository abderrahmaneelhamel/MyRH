package com.MyRH.Models.Mappers;

import com.MyRH.Models.DTOs.CompanyDto;
import com.MyRH.Models.Entities.Company;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper(componentModel = "spring")
public interface CompanyMapper {

    CompanyMapper INSTANCE = Mappers.getMapper(CompanyMapper.class);


    Company toEntity(CompanyDto CompanyDto);
}