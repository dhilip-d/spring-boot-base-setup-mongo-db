package com.basesetup.login.service;

import com.basesetup.login.dto.auth.LoginRequestDto;
import com.basesetup.login.dto.auth.SignupUserDto;
import com.basesetup.login.exception.ApplicationException;
import com.basesetup.login.model.User;
import com.basesetup.login.repository.RoleRepository;
import com.basesetup.login.repository.UserRepository;
import com.basesetup.login.utils.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public String signup(SignupUserDto input) {
        if (userRepository.existsByEmail(input.getEmail()))
            throw new ApplicationException("email id already exists");
        User user = new User();
        user.setUsername(input.getEmail());
        user.setFirstName(input.getFirstName());
        user.setLastName(input.getLastName());
        user.setEmail(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setCreatedBy(input.getEmail());
        if (userRepository.count() == 0)
            user.setRole(roleRepository.findByName(Constant.SUPER_ADMIN));
        else
            user.setRole(roleRepository.findByName(Constant.EMPLOYEE));
        user.setActive(true);
        userRepository.save(user);
        return "Register successful";
    }

    public User authenticate(LoginRequestDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getUsername(),
                        input.getPassword()
                )
        );
        return userRepository.findByEmail(input.getUsername())
                .orElseThrow();
    }
}
