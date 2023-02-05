package com.example.demodevops.controller;

import com.example.demodevops.dto.Greeting;
import com.example.demodevops.services.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
public class MainController {

    @Value("${server.port}")
    private int serverPort;

    @Value("${CONFIG_MAP_VALUE}")
    private String configMapValue;

    private final UserService userService;

    public MainController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public ResponseEntity<Greeting> getHello() throws UnknownHostException {
        final String hostname = InetAddress.getLocalHost().getHostName();

        return ResponseEntity.ok(Greeting.builder()
                .message("Hello!")
                .port(serverPort)
                .hostname(hostname)
                .valueFromConfigMap(configMapValue)
                .users(userService.allUsers())
                .build());
    }
}
