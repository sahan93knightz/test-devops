package com.example.demodevops.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class MainControllerTest {

    @Autowired
    private MainController mainController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void mainControllerIsNotNull() {
        assertThat(mainController).isNotNull();
    }

    @Test
    void getHelloShouldReturnGreetingHostNameAndPort() throws UnknownHostException {
        final String hostname = InetAddress.getLocalHost().getHostName();
        assertThat(restTemplate.getForObject("http://localhost:8080",
                String.class))
                .isEqualTo("Hello from " + hostname + ":8080");
    }
}