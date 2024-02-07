package cum.MyRH.Models.Mappers;

import cum.MyRH.Models.DTOs.ApplicantDto;
import cum.MyRH.Models.Entities.Applicant;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ApplicantMapper {

    ApplicantMapper INSTANCE = Mappers.getMapper(ApplicantMapper.class);

    Applicant toEntity(ApplicantDto ApplicantDto);

}