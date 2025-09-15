package com.basesetup.login.utils;

import com.basesetup.login.model.Role;
import com.basesetup.login.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class DefaultCreations {
    private final RoleRepository roleRepository;

    @PostConstruct
    public void initialize() {
        role();
    }

    private void role() {
        String[] roles = {Constant.SUPER_ADMIN, Constant.EMPLOYEE};
        for (String r : roles) {
            if (roleRepository.existsByName(r))
                return;
            Role role = new Role();
            role.setName(r);
            /*role.setPermissions(Set.of(
                    Permission.ROLE_CREATE,
                    Permission.ROLE_VIEW,
                    Permission.ROLE_UPDATE,
                    Permission.ROLE_DELETE,
                    Permission.USER_CREATE,
                    Permission.USER_VIEW,
                    Permission.USER_UPDATE,
                    Permission.USER_DELETE)
                    );*/

            role.setUserCreate(true);
            role.setUserView(true);
            role.setUserUpdate(true);
            role.setUserDelete(true);
            role.setRoleCreate(true);
            role.setRoleView(true);
            role.setRoleUpdate(true);
            role.setRoleDelete(true);

            role.setActive(true);
            role.setCreatedAt(LocalDateTime.now());
            role.setCreatedBy(Constant.SYSTEM);
            roleRepository.save(role);
        }
    }
}
