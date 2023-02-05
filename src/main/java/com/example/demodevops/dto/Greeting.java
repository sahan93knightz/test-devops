package com.example.demodevops.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class Greeting {

    private String message;
    private String hostname;
    private int port;
    private String valueFromConfigMap;
    private List<UserDto> users;
}
