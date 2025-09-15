package com.basesetup.login.repository;

import com.basesetup.login.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByName(String superAdmin);

    boolean existsByName(String superAdmin);

    boolean existsByNameAndIsActiveTrue(String name);

    Page<Role> findAllByIsActiveTrue(Pageable pageable);
}
