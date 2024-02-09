package cum.MyRH.Models.DTOs;

import cum.MyRH.Models.Entities.Question;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

@Data
@Value
public class TestDto implements Serializable {
    Long id;
    String name;
    List<Question> questions;
}