package com.basesetup.login.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "role")
public class Role extends BaseProperties {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<User> users = new HashSet<>();
    //private Set<Permission> permissions;
    private boolean userCreate;
    private boolean userView;
    private boolean userUpdate;
    private boolean userDelete;
    private boolean roleCreate;
    private boolean roleView;
    private boolean roleUpdate;
    private boolean roleDelete;

}
