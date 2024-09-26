package com.desafioTecnico.controller;

import com.desafioTecnico.dtos.UserRequestDto;
import com.desafioTecnico.models.User;
import com.desafioTecnico.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private User user;
    private UserRequestDto userRequestDto;
    private UUID userId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userId = UUID.randomUUID();
        user = new User(userId, "Test User", "test@example.com", "password", new ArrayList<>(), null, null, null, null, true);
        userRequestDto = new UserRequestDto("Test User", "test@example.com", "password", true, new ArrayList<>());
    }

    @Test
    void registerUser_ShouldReturnCreatedUser() {
        when(userService.registerUser(any(UserRequestDto.class))).thenReturn(user);

        ResponseEntity<User> response = userController.registerUser(userRequestDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(user, response.getBody());
        verify(userService, times(1)).registerUser(userRequestDto);
    }

    @Test
    void getUserById_ShouldReturnUser() {
        when(userService.findById(userId)).thenReturn(user);

        ResponseEntity<User> response = userController.getUserById(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
        verify(userService, times(1)).findById(userId);
    }

    @Test
    void getAllUsers_ShouldReturnListOfUsers() {
        List<User> users = new ArrayList<>();
        users.add(user);
        when(userService.findAll()).thenReturn(users);

        ResponseEntity<List<User>> response = userController.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());
        verify(userService, times(1)).findAll();
    }

    @Test
    void updateUser_ShouldReturnUpdatedUser() {
        when(userService.updateUser(any(UUID.class), any(UserRequestDto.class))).thenReturn(user);

        ResponseEntity<User> response = userController.updateUser(userId, userRequestDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
        verify(userService, times(1)).updateUser(userId, userRequestDto);
    }

    @Test
    void deleteUser_ShouldReturnNoContent() {
        doNothing().when(userService).deleteUser(userId);

        ResponseEntity<Void> response = userController.deleteUser(userId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userService, times(1)).deleteUser(userId);
    }
}
