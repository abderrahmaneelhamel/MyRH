package com.MyRH.Models.DTOs;

import lombok.Data;
import lombok.Value;

import java.io.Serializable;

@Data
@Value
public class ApplicantDto implements Serializable {
    Long id;
    String firstName;
    String lastName;
    String email;
    String message;
    String level;
    String profile;
    String city;
    int jobId;
}