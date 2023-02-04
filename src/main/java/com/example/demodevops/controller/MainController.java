package com.example.demodevops.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;

@RestController
public class MainController {

    @Value("${server.port}")
    private int serverPort;

    @GetMapping("/")
    public ResponseEntity<String> getHello() {
        final String hostname = InetAddress.getLoopbackAddress().getHostName();

        return ResponseEntity.ok("Hello from " + hostname + ":" + serverPort);
    }
}
