package com.basesetup.login.dto.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePasswordDto {
    @NotEmpty(message = "old password is required")
    private String oldPassword;
    @NotEmpty(message = "new password is required")
    private String newPassword;
}
