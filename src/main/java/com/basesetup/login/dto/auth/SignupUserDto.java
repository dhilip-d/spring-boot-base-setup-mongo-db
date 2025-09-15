package com.basesetup.login.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SignupUserDto {
    @NotEmpty(message = "first name is required")
    private String firstName;
    @NotEmpty(message = "last name is required")
    private String lastName;
    @Email(message = "invalid email format")
    private String email;
    @NotEmpty(message = "password is required")
    private String password;
}