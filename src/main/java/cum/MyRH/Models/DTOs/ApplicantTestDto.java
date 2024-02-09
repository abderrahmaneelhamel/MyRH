package cum.MyRH.Models.DTOs;

import lombok.Data;
import lombok.Value;

import java.io.Serializable;
@Data
@Value
public class ApplicantTestDto implements Serializable {
    Long id;
    int attempts;
    boolean passed;
}