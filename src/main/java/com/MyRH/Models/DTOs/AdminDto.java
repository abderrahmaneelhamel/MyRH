package com.MyRH.Models.DTOs;

import lombok.Data;
import lombok.Value;

import java.io.Serializable;

@Data
@Value
public class AdminDto implements Serializable {
    Long id;
    String name;
    String email;
    String password;
}