package com.basesetup.login.service;

import com.basesetup.login.config.UserContextHolder;
import com.basesetup.login.dto.user.UpdatePasswordDto;
import com.basesetup.login.dto.user.UserRequestDto;
import com.basesetup.login.dto.user.UserResponseDto;
import com.basesetup.login.exception.ApplicationException;
import com.basesetup.login.model.User;
import com.basesetup.login.repository.UserRepository;
import com.basesetup.login.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String updateUser(UserRequestDto userRequestDto) {
        User user = userRepository.findById(userRequestDto.getId())
                .orElseThrow(() -> new ApplicationException("user not found"));
        user.setFirstName(userRequestDto.getFirstName());
        user.setLastName(userRequestDto.getFirstName());
        user.setUpdatedAt(LocalDateTime.now());
        user.setUpdatedBy(UserContextHolder.getUserContext().getEmail());
        userRepository.save(user);
        return "User updated";
    }

    public PageResponse<List<UserResponseDto>> findAllUser(int pageNo, int pageSize, String search) {
        PageResponse<List<UserResponseDto>> pageResponse = new PageResponse<>();
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        List<UserResponseDto> userResponseList = new ArrayList<>();
        Page<User> users = userRepository.findAllByIsActiveTrue(pageable);
        users.forEach(user -> {
            UserResponseDto userResponseDto = new UserResponseDto();
            BeanUtils.copyProperties(user, userResponseDto);
            userResponseList.add(userResponseDto);
        });
        pageResponse.setData(userResponseList);
        pageResponse.setTotalRecordCount(users.getTotalElements());
        pageResponse.setTotalPages(users.getTotalPages());
        pageResponse.setHasPrevious(users.hasPrevious());
        pageResponse.setHasNext(users.hasNext());
        return pageResponse;
    }

    public String deleteUser(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ApplicationException("user not found"));
        user.setActive(false);
        userRepository.save(user);
        return "User deleted";
    }

    public UserResponseDto findOneUser(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ApplicationException("user not found"));
        UserResponseDto userResponseDto = new UserResponseDto();
        BeanUtils.copyProperties(user, userResponseDto);
        return userResponseDto;
    }

    public String updatePassword(UpdatePasswordDto updatePasswordDto) {
        User user = userRepository.findById(UserContextHolder.getUserContext().getId())
                .orElseThrow(() -> new ApplicationException("user not found"));
        if (!passwordEncoder.matches(updatePasswordDto.getOldPassword(), user.getPassword()))
            throw new ApplicationException("old password doesn't match");
        user.setPassword(passwordEncoder.encode(updatePasswordDto.getNewPassword()));
        userRepository.save(user);
        return "Password updated";
    }
}