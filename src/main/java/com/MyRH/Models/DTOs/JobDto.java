package com.MyRH.Models.DTOs;

import com.MyRH.Models.Enums.Status;
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
    int CompanyId;
}