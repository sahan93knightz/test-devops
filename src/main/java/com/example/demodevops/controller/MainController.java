package com.example.demodevops.controller;

import com.example.demodevops.dto.Greeting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
public class MainController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

    @Value("${server.port}")
    private int serverPort;

    @Value("${CONFIG_MAP_VALUE}")
    private String configMapValue;

    @GetMapping("/")
    public ResponseEntity<Greeting> getHello() throws UnknownHostException {
        LOGGER.info("Request received to getHello()");

        final String hostname = InetAddress.getLocalHost().getHostName();

        return ResponseEntity.ok(Greeting.builder()
                .message("Hello!")
                .port(serverPort)
                .hostname(hostname)
                .valueFromConfigMap(configMapValue)
                .build());
    }
}
