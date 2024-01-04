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
    String password;
    String level;
    String profile;
    String city;
}