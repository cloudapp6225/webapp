package com.cloudnativewebapp.webapp.Service;

import com.cloudnativewebapp.webapp.DTO.UserDTO;
import com.cloudnativewebapp.webapp.Entity.User;
import com.cloudnativewebapp.webapp.Exception.*;
import com.cloudnativewebapp.webapp.Repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @BeforeEach
    void setUp() throws InvalidEmailAddressException, InvalidUserInputException, UserAlreadyExistsException, DatabaseException {
        User userBuilder = User.builder()
                        .first_name("joe")
                        .last_name("doe")
                        .password("XXXXXX")
                        .username("jie.doe@gmail.com")
                        .build();
//        Mockito.when(userRepository.findByUsername("joe.doe@gmail.com")).thenReturn(userBuilder);
        userService.createUser(userBuilder);
    }

    @Test
    void createUser() throws UserNotFoundException {
        String userName = "jie.doe@gmail.com";
        UserDTO userDTO = userService.getUserByUserName(userName);
        assertEquals(userDTO.getUsername(), userName);
    }
}