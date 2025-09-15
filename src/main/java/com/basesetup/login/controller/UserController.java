package com.basesetup.login.controller;

import com.basesetup.login.config.UserContextHolder;
import com.basesetup.login.dto.user.UpdatePasswordDto;
import com.basesetup.login.dto.user.UserRequestDto;
import com.basesetup.login.dto.user.UserResponseDto;
import com.basesetup.login.exception.ApplicationException;
import com.basesetup.login.model.User;
import com.basesetup.login.response.PageResponse;
import com.basesetup.login.service.UserService;
import com.basesetup.login.utils.Constant;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/user")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<User> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        return ResponseEntity.ok(currentUser);
    }

    @PutMapping
    private ResponseEntity<String> updateUser(@RequestBody @Valid UserRequestDto userRequestDto) {
        if (!UserContextHolder.getUserContext().getRole().isUserUpdate())
            throw new ApplicationException(Constant.NOT_AUTHORIZED);
        return ResponseEntity.ok(userService.updateUser(userRequestDto));
    }

    @GetMapping("/all")
    private PageResponse<List<UserResponseDto>> findAllUser(@RequestParam int pageNo,
                                                            @RequestParam int pageSize,
                                                            @RequestParam(required = false) String search) {
        if (!UserContextHolder.getUserContext().getRole().isUserView())
            return new PageResponse<>();
        return userService.findAllUser(pageNo, pageSize, search);
    }

    @GetMapping("/one")
    private ResponseEntity<UserResponseDto> findOneUser(@RequestParam int id) {
        if (!UserContextHolder.getUserContext().getRole().isUserView())
            throw new ApplicationException(Constant.NOT_AUTHORIZED);
        return ResponseEntity.ok(userService.findOneUser(id));
    }

    @PutMapping("/password")
    private ResponseEntity<String> updatePassword(@RequestBody UpdatePasswordDto updatePasswordDto) {
        if (!UserContextHolder.getUserContext().getRole().isUserUpdate())
            throw new ApplicationException(Constant.NOT_AUTHORIZED);
        return ResponseEntity.ok(userService.updatePassword(updatePasswordDto));
    }

    @DeleteMapping
    private ResponseEntity<String> deleteUser(@RequestParam int id) {
        if (!UserContextHolder.getUserContext().getRole().isUserDelete())
            throw new ApplicationException(Constant.NOT_AUTHORIZED);
        return ResponseEntity.ok(userService.deleteUser(id));
    }
}
