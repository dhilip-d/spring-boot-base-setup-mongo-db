package com.basesetup.login.dto.role;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RoleResponseDto {
    private int id;
    private String name;
    private boolean userCreate;
    private boolean userView;
    private boolean userUpdate;
    private boolean userDelete;
    private boolean roleCreate;
    private boolean roleView;
    private boolean roleUpdate;
    private boolean roleDelete;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
}
