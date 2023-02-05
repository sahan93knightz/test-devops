package com.example.demodevops.controller;

import com.example.demodevops.dto.Greeting;
import com.example.demodevops.dto.UserDto;
import com.example.demodevops.services.UserService;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class MainControllerTest {

    @Autowired
    private MainController mainController;

    @Autowired
    private TestRestTemplate restTemplate;

    final List<UserDto> users = new ArrayList<>();

    @BeforeAll
    public static void init() {
        System.setProperty("CONFIG_MAP_VALUE", "MY_VALUE");
    }
    @MockBean
    private UserService userService;


    @Test
    void mainControllerIsNotNull() {
        assertThat(mainController).isNotNull();
    }

    @Test
    void getHelloShouldReturnGreetingHostNameAndPort() throws UnknownHostException {
        final List<UserDto> users = Stream.of(1L, 2L, 3L)
                .map(i -> UserDto
                        .builder()
                        .id(i)
                        .username("username" + i)
                        .email("user" + i + "@gmail.com")
                        .build()
                )
                .collect(Collectors.toList());

        Mockito.when(userService.allUsers()).thenReturn(users);

        final String hostname = InetAddress.getLocalHost().getHostName();

        Consumer<Greeting> hostnameAssertion = greeting -> assertThat(greeting.getHostname()).isEqualTo(hostname);
        Consumer<Greeting> portAssertion = greeting -> assertThat(greeting.getPort()).isEqualTo(8080);
        Consumer<Greeting> messageAssertion = greeting -> assertThat(greeting.getMessage()).isEqualTo("Hello!");
        Consumer<Greeting> configMapValueAssertion = greeting -> assertThat(greeting.getValueFromConfigMap()).isEqualTo("MY_VALUE");
        Consumer<Greeting> usersSizeAssertion = greeting -> assertThat(greeting.getUsers().size()).isEqualTo(users.size());
        Consumer<Greeting> usersAssertion = greeting -> assertThat(greeting.getUsers())
                .extracting(UserDto::getId, UserDto::getUsername, UserDto::getEmail)
                .containsExactly(
                        Tuple.tuple(1L, "username1", "user1@gmail.com"),
                        Tuple.tuple(2L, "username2", "user2@gmail.com"),
                        Tuple.tuple(3L, "username3", "user3@gmail.com")
                );

        assertThat(restTemplate.getForObject("http://localhost:8080", Greeting.class))
                .satisfies(
                        hostnameAssertion,
                        portAssertion,
                        messageAssertion,
                        configMapValueAssertion,
                        usersSizeAssertion,
                        usersAssertion);
    }

}