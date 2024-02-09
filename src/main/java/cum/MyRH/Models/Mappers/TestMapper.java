package cum.MyRH.Models.Mappers;

import cum.MyRH.Models.DTOs.TestDto;
import cum.MyRH.Models.Entities.Test;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper(componentModel = "spring")
public interface TestMapper {

    TestMapper INSTANCE = Mappers.getMapper(TestMapper.class);

    TestDto toDto(Test test);

    Test toEntity(TestDto testDto);
}