package com.basesetup.login.dto.auth;

import com.basesetup.login.model.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserContextDto {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private Role role;
}
