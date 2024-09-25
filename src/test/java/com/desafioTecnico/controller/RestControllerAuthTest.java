package com.desafioTecnico.controller;

import com.desafioTecnico.security.JwtGenerador;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class RestControllerAuthTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private IRolRepository rolesRepository;

    @Mock
    private IUserRepository usuariosRepository;

    @Mock
    private JwtGenerador jwtGenerador;

    private RestControllerAuth restControllerAuth;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        restControllerAuth = new RestControllerAuth(authenticationManager, passwordEncoder, rolesRepository, usuariosRepository, jwtGenerador);
    }

    @Test
    public void testRegistrar() {
        DtoRegister dtoRegister = new DtoRegister();
        dtoRegister.setUsername("testuser");
        dtoRegister.setPassword("testpassword");

        when(usuariosRepository.existsByUsername(eq("testuser"))).thenReturn(false);
        when(rolesRepository.findByName(eq("USER"))).thenReturn(Optional.of(new Role()));
        when(passwordEncoder.encode(eq("testpassword"))).thenReturn("encodedPassword");
        when(usuariosRepository.save(any(User.class))).thenReturn(new User());

        ResponseEntity<String> response = restControllerAuth.registrar(dtoRegister);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Registro de usuario exitoso", response.getBody());

        verify(usuariosRepository, times(1)).existsByUsername(eq("testuser"));
        verify(rolesRepository, times(1)).findByName(eq("USER"));
        verify(passwordEncoder, times(1)).encode(eq("testpassword"));
        verify(usuariosRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testRegistrar_UsuarioExistente() {
        DtoRegister dtoRegister = new DtoRegister();
        dtoRegister.setUsername("existinguser");
        dtoRegister.setPassword("testpassword");

        when(usuariosRepository.existsByUsername(eq("existinguser"))).thenReturn(true);

        ResponseEntity<String> response = restControllerAuth.registrar(dtoRegister);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("el usuario ya existe, intenta con otro", response.getBody());

        verify(usuariosRepository, times(1)).existsByUsername(eq("existinguser"));
        verify(rolesRepository, never()).findByName(anyString());
        verify(passwordEncoder, never()).encode(anyString());
        verify(usuariosRepository, never()).save(any(User.class));
    }

    @Test
    public void testRegistrarAdmin() {
        DtoRegister dtoRegister = new DtoRegister();
        dtoRegister.setUsername("testadmin");
        dtoRegister.setPassword("testpassword");

        when(usuariosRepository.existsByUsername(eq("testadmin"))).thenReturn(false);
        when(rolesRepository.findByName(eq("ADMIN"))).thenReturn(Optional.of(new Role()));
        when(passwordEncoder.encode(eq("testpassword"))).thenReturn("encodedPassword");
        when(usuariosRepository.save(any(User.class))).thenReturn(new User());

        ResponseEntity<String> response = restControllerAuth.registrarAdmin(dtoRegister);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Registro de admin exitoso", response.getBody());

        verify(usuariosRepository, times(1)).existsByUsername(eq("testadmin"));
        verify(rolesRepository, times(1)).findByName(eq("ADMIN"));
        verify(passwordEncoder, times(1)).encode(eq("testpassword"));
        verify(usuariosRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testRegistrarAdmin_UsuarioExistente() {
        DtoRegister dtoRegister = new DtoRegister();
        dtoRegister.setUsername("existingadmin");
        dtoRegister.setPassword("testpassword");

        when(usuariosRepository.existsByUsername(eq("existingadmin"))).thenReturn(true);

        ResponseEntity<String> response = restControllerAuth.registrarAdmin(dtoRegister);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("el usuario ya existe, intenta con otro", response.getBody());

        verify(usuariosRepository, times(1)).existsByUsername(eq("existingadmin"));
        verify(rolesRepository, never()).findByName(anyString());
        verify(passwordEncoder, never()).encode(anyString());
        verify(usuariosRepository, never()).save(any(User.class));
    }
}
