package com.MyRH.Models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;

@Value
@Data
@AllArgsConstructor
public class CompanyDto implements Serializable {
    Long id;
    String name;
    String email;
    String password;
    String address;
    String phone;
}