package com.basesetup.login.dto.auth;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequestDto {
    @Email(message = "invalid email format")
    private String username;
    @NotEmpty(message = "password is required")
    private String password;
}