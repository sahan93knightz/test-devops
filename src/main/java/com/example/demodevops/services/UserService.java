package com.example.demodevops.services;

import com.example.demodevops.dto.UserDto;
import com.example.demodevops.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDto> allUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> UserDto
                        .builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .build()
                ).collect(Collectors.toList());
    }
}
