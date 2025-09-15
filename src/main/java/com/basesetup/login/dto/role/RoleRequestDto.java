package com.basesetup.login.dto.role;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RoleRequestDto {
    private int id;
    @NotEmpty(message = "name is required")
    private String name;
    private boolean userCreate;
    private boolean userView;
    private boolean userUpdate;
    private boolean userDelete;
    private boolean roleCreate;
    private boolean roleView;
    private boolean roleUpdate;
    private boolean roleDelete;
    private boolean isActive;
}
