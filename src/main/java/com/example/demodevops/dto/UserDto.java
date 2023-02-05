package com.example.demodevops.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserDto {
    private long id;
    private String username;
    private String email;
}
