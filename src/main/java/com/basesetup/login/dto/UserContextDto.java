package com.basesetup.login.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserContextDto {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
}
