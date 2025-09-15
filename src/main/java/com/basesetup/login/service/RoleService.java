package com.basesetup.login.service;

import com.basesetup.login.config.UserContextHolder;
import com.basesetup.login.dto.role.RoleRequestDto;
import com.basesetup.login.dto.role.RoleResponseDto;
import com.basesetup.login.exception.ApplicationException;
import com.basesetup.login.model.Role;
import com.basesetup.login.repository.RoleRepository;
import com.basesetup.login.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
    public final RoleRepository roleRepository;

    public String createRole(RoleRequestDto roleRequestDto) {
        String name = roleRequestDto.getName().replace(" ", "").toLowerCase();
        if (roleRepository.existsByNameAndIsActiveTrue(name))
            throw new ArithmeticException("role already exists");
        Role role = new Role();
        role.setName(name);
        role.setActive(true);
        role.setCreatedAt(LocalDateTime.now());
        role.setCreatedBy(UserContextHolder.getUserContext().getEmail());
        roleRepository.save(role);
        return "Role created";
    }

    public String updateRole(RoleRequestDto roleRequestDto) {
        Role role = roleRepository.findById(roleRequestDto.getId())
                .orElseThrow(() -> new ApplicationException("role not found"));
        role.setName(roleRequestDto.getName());
        role.setUpdatedAt(LocalDateTime.now());
        role.setUpdatedBy(UserContextHolder.getUserContext().getEmail());
        roleRepository.save(role);
        return "Role updated";
    }

    public PageResponse<List<RoleResponseDto>> findAllRole(int pageNo, int pageSize, String search) {
        PageResponse<List<RoleResponseDto>> pageResponse = new PageResponse<>();
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        List<RoleResponseDto> roleResponseList = new ArrayList<>();
        Page<Role> roles = roleRepository.findAllByIsActiveTrue(pageable);
        roles.forEach(role -> {
            RoleResponseDto roleResponseDto = new RoleResponseDto();
            BeanUtils.copyProperties(role, roleResponseDto);
            roleResponseList.add(roleResponseDto);
        });
        pageResponse.setData(roleResponseList);
        pageResponse.setTotalRecordCount(roles.getTotalElements());
        pageResponse.setTotalPages(roles.getTotalPages());
        pageResponse.setHasPrevious(roles.hasPrevious());
        pageResponse.setHasNext(roles.hasNext());
        return pageResponse;
    }

    public String deleteRole(int id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ApplicationException("role not found"));
        role.setActive(false);
        roleRepository.save(role);
        return "Role deleted";
    }

    public RoleResponseDto findOneRole(int id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ApplicationException("role not found"));
        RoleResponseDto roleResponseDto = new RoleResponseDto();
        BeanUtils.copyProperties(role, roleResponseDto);
        return roleResponseDto;
    }
}
