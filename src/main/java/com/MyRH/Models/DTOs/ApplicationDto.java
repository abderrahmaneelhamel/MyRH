package com.MyRH.Models.DTOs;

import com.MyRH.Models.Enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationDto {
    Long id;
    Date date;
    String message;
    Long job_id;
    Long applicant_id;
    Status status;
}