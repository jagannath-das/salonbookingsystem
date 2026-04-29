package com.proj.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class logindto {

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String emailid;

    @NotBlank(message = "Password is required")
    private String password;
}