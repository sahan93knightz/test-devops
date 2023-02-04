package com.example.demodevops.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Greeting {

    private String message;
    private String hostname;
    private int port;
    private String valueFromConfigMap;
}
