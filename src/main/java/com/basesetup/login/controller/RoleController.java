package com.basesetup.login.controller;

import com.basesetup.login.config.UserContextHolder;
import com.basesetup.login.dto.role.RoleRequestDto;
import com.basesetup.login.dto.role.RoleResponseDto;
import com.basesetup.login.exception.ApplicationException;
import com.basesetup.login.response.PageResponse;
import com.basesetup.login.service.RoleService;
import com.basesetup.login.utils.Constant;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/role")
public class RoleController {
    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<String> createRole(@RequestBody @Valid RoleRequestDto roleRequestDto) {
        if (!UserContextHolder.getUserContext().getRole().isRoleCreate())
            throw new ApplicationException(Constant.NOT_AUTHORIZED);
        return ResponseEntity.ok(roleService.createRole(roleRequestDto));
    }

    @PutMapping
    private ResponseEntity<String> updateRole(@RequestBody @Valid RoleRequestDto roleRequestDto) {
        if (!UserContextHolder.getUserContext().getRole().isRoleUpdate())
            throw new ApplicationException(Constant.NOT_AUTHORIZED);
        return ResponseEntity.ok(roleService.updateRole(roleRequestDto));
    }

    @GetMapping("/all")
    private PageResponse<List<RoleResponseDto>> findAllRole(@RequestParam int pageNo,
                                                            @RequestParam int pageSize,
                                                            @RequestParam(required = false) String search) {
        if (!UserContextHolder.getUserContext().getRole().isRoleView())
            return new PageResponse<>();
        return roleService.findAllRole(pageNo, pageSize, search);
    }

    @GetMapping("/one")
    private ResponseEntity<RoleResponseDto> findOneRole(@RequestParam int id) {
        if (!UserContextHolder.getUserContext().getRole().isRoleView())
            throw new ApplicationException(Constant.NOT_AUTHORIZED);
        return ResponseEntity.ok(roleService.findOneRole(id));
    }

    @DeleteMapping
    private ResponseEntity<String> deleteRole(@RequestParam int id) {
        if (!UserContextHolder.getUserContext().getRole().isRoleDelete())
            throw new ApplicationException(Constant.NOT_AUTHORIZED);
        return ResponseEntity.ok(roleService.deleteRole(id));
    }
}
