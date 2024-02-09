package cum.MyRH.Models.DTOs;

import lombok.Data;
import lombok.Value;

import java.io.Serializable;

@Data
@Value
public class BadgeDto implements Serializable {
    Long id;
    String name;
    String description;
}