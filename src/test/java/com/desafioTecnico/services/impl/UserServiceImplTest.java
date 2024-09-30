package com.desafioTecnico.services.impl;

import com.desafioTecnico.dtos.PhoneDto;
import com.desafioTecnico.dtos.UserRequestDto;
import com.desafioTecnico.exceptions.EmailValidationException;
import com.desafioTecnico.models.User;
import com.desafioTecnico.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceImplTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(userService, "passwordRegex", "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=]).{8,20}$");
    }

    @Test
    void registerUser_ShouldRegisterUser_WhenEmailIsUnique() {
        List<PhoneDto> phones = new ArrayList<>();
        phones.add(new PhoneDto("123456789", "1", "123"));

        UserRequestDto userRequestDto = new UserRequestDto("Jerry Támara", "jerry@gmail.com", "P@ssw0rd123", true, phones);
        User userToSave = new User();
        userToSave.setName(userRequestDto.getName());
        userToSave.setEmail(userRequestDto.getEmail());
        userToSave.setPassword(userRequestDto.getPassword());
        userToSave.setIsActive(true);
        userToSave.setCreated(LocalDateTime.now());
        userToSave.setModified(LocalDateTime.now());
        userToSave.setLastLogin(LocalDateTime.now());

        when(userRepository.findByEmail(userRequestDto.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(userToSave);

        User registeredUser = userService.registerUser(userRequestDto);

        assertNotNull(registeredUser);
        assertEquals(userRequestDto.getName(), registeredUser.getName());
        assertEquals(userRequestDto.getEmail(), registeredUser.getEmail());
        verify(userRepository).save(any(User.class));
    }


    @Test
    void registerUser_ShouldThrowEmailValidationException_WhenEmailExists() {
        UserRequestDto userRequestDto = new UserRequestDto("Jerry Támara", "jerry@gmail.com", "Password123!", true, new ArrayList<>());
        User existingUser = new User();
        existingUser.setEmail("jerry@gmail.com");

        when(userRepository.findByEmail("jerry@gmail.com")).thenReturn(Optional.of(existingUser));

        assertThrows(EmailValidationException.class, () -> userService.registerUser(userRequestDto));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void findById_ShouldReturnUser_WhenUserExists() {
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User result = userService.findById(userId);
        assertEquals(userId, result.getId());
    }

    @Test
    void findById_ShouldThrowRuntimeException_WhenUserNotFound() {
        UUID userId = UUID.randomUUID();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.findById(userId));
    }

    @Test
    void updateUser_ShouldUpdateUser_WhenUserExists() {
        UUID userId = UUID.randomUUID();
        UserRequestDto userRequestDto = new UserRequestDto("Jerry Támara", "jerry@gmail.com", "P@ssw0rd123", true, new ArrayList<>());
        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setEmail("old@gmail.com");
        existingUser.setName("Old Name");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        User updatedUser = userService.updateUser(userId, userRequestDto);

        assertEquals("Jerry Támara", updatedUser.getName());
        assertEquals("jerry@gmail.com", updatedUser.getEmail());
        verify(userRepository).save(existingUser);
    }

    @Test
    void deleteUser_ShouldCallDeleteById_WhenUserExists() {
        UUID userId = UUID.randomUUID();

        userService.deleteUser(userId);

        verify(userRepository).deleteById(userId);
    }

}
