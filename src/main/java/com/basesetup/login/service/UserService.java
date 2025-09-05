package com.basesetup.login.service;

import com.basesetup.login.model.User;
import com.basesetup.login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> allUsers() {
        System.out.println();
        return new ArrayList<>(userRepository.findAll());

    }
}