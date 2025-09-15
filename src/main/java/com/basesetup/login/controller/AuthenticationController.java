package com.basesetup.login.controller;

import com.basesetup.login.dto.auth.LoginRequestDto;
import com.basesetup.login.dto.auth.LoginResponseDto;
import com.basesetup.login.dto.auth.SignupUserDto;
import com.basesetup.login.model.User;
import com.basesetup.login.service.AuthenticationService;
import com.basesetup.login.service.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<String> register(@RequestBody @Valid SignupUserDto registerUserDto) {
        return ResponseEntity.ok(authenticationService.signup(registerUserDto));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> authenticate(@RequestBody LoginRequestDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);
        LoginResponseDto loginResponse = new LoginResponseDto();
        loginResponse.setToken(jwtService.generateToken(authenticatedUser));
        loginResponse.setExpiresIn(jwtService.getExpirationTime());
        return ResponseEntity.ok(loginResponse);
    }
}