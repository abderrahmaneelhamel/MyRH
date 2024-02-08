package cum.MyRH.Models.Mappers;

import cum.MyRH.Models.DTOs.CompanyDto;
import cum.MyRH.Models.Entities.Company;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper(componentModel = "spring")
public interface CompanyMapper {

    CompanyMapper INSTANCE = Mappers.getMapper(CompanyMapper.class);


    Company toEntity(CompanyDto CompanyDto);
}