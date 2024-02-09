package cum.MyRH.Models.DTOs;

import cum.MyRH.Models.Enums.QuestionStatus;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;

@Data
@Value
public class AnswerDto implements Serializable {
    Long id;
    String content;
    QuestionStatus status;
}