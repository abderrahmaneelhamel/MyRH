package com.MyRH.Models.DTOs;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Value
@Data
@Setter
@AllArgsConstructor
public class CompanyDto implements Serializable {
    Long id;

    @NotBlank(message = "Name is mandatory")
    @Size(max = 100, message = "Name must be a maximum of 100 characters")
    private String name;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Password must be between 8 and 50 characters")
    private String password;

    String address;

    @NotNull(message = "Phone is mandatory")
    @NotBlank(message = "Phone is mandatory")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone should be valid")
    private String phone;

    private MultipartFile image;
}