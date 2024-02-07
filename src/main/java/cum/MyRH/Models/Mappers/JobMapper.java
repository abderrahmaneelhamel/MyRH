package cum.MyRH.Models.Mappers;

import cum.MyRH.Models.DTOs.JobDto;
import cum.MyRH.Models.Entities.Company;
import cum.MyRH.Models.Entities.Job;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface JobMapper {

    JobMapper INSTANCE = Mappers.getMapper(JobMapper.class);

    @Mapping(target = "company.id", source = "companyId")
    Job toEntity(JobDto JobDto);

    @Mapping(target = "id", source = "company.id")
    JobDto toDto(Job Job);

    default List<Long> mapJobToIds(List<Job> JobList) {
        return JobList.stream().map(Job::getId).collect(Collectors.toList());
    }

    default List<Company> mapIdsToCompanies(List<Long> CompanyIds) {
        return CompanyIds.stream().map(id -> Company.builder().id(id).build()).collect(Collectors.toList());
    }

    default List<Long> mapCompaniesToIds(List<Company> Companies) {
        return Companies.stream().map(Company::getId).collect(Collectors.toList());
    }
}