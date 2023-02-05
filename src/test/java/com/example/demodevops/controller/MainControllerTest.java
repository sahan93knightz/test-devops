package com.example.demodevops.controller;

import com.example.demodevops.dto.Greeting;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class MainControllerTest {

    @Autowired
    private MainController mainController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ServerProperties serverProperties;

    @BeforeAll
    public static void init() {
        System.setProperty("CONFIG_MAP_VALUE", "MY_VALUE");
    }

    @Test
    void mainControllerIsNotNull() {
        assertThat(mainController).isNotNull();
    }

    @Test
    void getHelloShouldReturnGreetingHostNameAndPort() throws UnknownHostException {
        final String hostname = InetAddress.getLocalHost().getHostName();

        Consumer<Greeting> hostnameAssertion = greeting -> assertThat(greeting.getHostname()).isEqualTo(hostname);
        Consumer<Greeting> portAssertion = greeting -> assertThat(greeting.getPort()).isEqualTo(serverProperties.getPort());
        Consumer<Greeting> messageAssertion = greeting -> assertThat(greeting.getMessage()).isEqualTo("Hello!");
        Consumer<Greeting> configMapValueAssertion = greeting -> assertThat(greeting.getValueFromConfigMap()).isEqualTo("MY_VALUE");

        assertThat(restTemplate.getForObject("http://localhost:" + serverProperties.getPort(), Greeting.class))
                .satisfies(
                        hostnameAssertion,
                        portAssertion,
                        messageAssertion,
                        configMapValueAssertion);
    }
}