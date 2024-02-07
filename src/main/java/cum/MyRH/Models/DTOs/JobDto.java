package cum.MyRH.Models.DTOs;

import cum.MyRH.Models.Enums.Status;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;

@Data
@Value
public class JobDto implements Serializable {
    Long id;
    String title;
    String description;
    String level;
    int salary;
    String profile;
    String city;
    Status status;
    Long CompanyId;
}