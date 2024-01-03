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

//    @Mapping(target = "jobs.id", source = "jobId")
    Applicant toEntity(ApplicantDto ApplicantDto);

//    @Mapping(target = "id", source = "jobs.id")
    ApplicantDto toDto(Applicant Applicant);
//
//    default List<Long> mapApplicantToIds(List<Applicant> ApplicantList) {
//        return ApplicantList.stream().map(Applicant::getId).collect(Collectors.toList());
//    }
//
//    default List<Job> mapIdsToJobs(List<Long> JobIds) {
//        return JobIds.stream().map(id -> Job.builder().id(id).build()).collect(Collectors.toList());
//    }
//
//    default List<Long> mapJobsToIds(List<Job> Jobs) {
//        return Jobs.stream().map(Job::getId).collect(Collectors.toList());
//    }
}