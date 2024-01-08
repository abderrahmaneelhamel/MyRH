package com.MyRH.Models.Mappers;

import com.MyRH.Models.DTOs.ApplicantDto;
import com.MyRH.Models.Entities.Job;
import com.MyRH.Models.Entities.Applicant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ApplicantMapper {

    ApplicantMapper INSTANCE = Mappers.getMapper(ApplicantMapper.class);

    Applicant toEntity(ApplicantDto ApplicantDto);

}