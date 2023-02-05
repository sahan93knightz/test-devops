package com.example.demodevops.services;

import com.example.demodevops.dto.UserDto;
import com.example.demodevops.model.User;
import com.example.demodevops.repositories.UserRepository;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class UserServiceTest {

    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    public void beforeEach() {
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    @Test
    void allUsers() {

        final List<User> users = Stream.of(1L, 2L, 3L)
                .map(i -> {
                            User u = new User();
                            u.setId(i);
                            u.setUsername("username" + i);
                            u.setEmail("user" + i + "@gmail.com");
                            return u;
                        }
                )
                .collect(Collectors.toList());

        Mockito.when(userRepository.findAll()).thenReturn(users);


        List<UserDto> userDtos = userService.allUsers();

        assertThat(userDtos)
                .extracting(UserDto::getId, UserDto::getUsername, UserDto::getEmail)
                .containsExactly(
                        Tuple.tuple(1L, "username1", "user1@gmail.com"),
                        Tuple.tuple(2L, "username2", "user2@gmail.com"),
                        Tuple.tuple(3L, "username3", "user3@gmail.com")
                );
    }
}