package com.desafioTecnico.controller;

import com.desafioTecnico.dtos.UserRequestDto;
import com.desafioTecnico.models.User;
import com.desafioTecnico.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

/**
 * Controlador para manejar las operaciones relacionadas con los usuarios.
 * Proporciona endpoints para registrar nuevos usuarios y realizar otras operaciones relacionadas.
 */
@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {

    private final UserService userService;

    /**
     * Constructor que inicializa el controlador con el servicio de usuarios.
     *
     * @param userService Servicio de usuarios utilizado para las operaciones relacionadas con los usuarios.
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Endpoint para registrar un nuevo usuario.
     *
     * @param userRequestDto DTO que contiene la información del usuario a registrar.
     * @return ResponseEntity que contiene el usuario registrado y un código de estado HTTP.
     *         Si hay un error, se devuelve un mensaje de error y un código de estado BAD_REQUEST.
     */
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody @Valid UserRequestDto userRequestDto) {
        User user = userService.registerUser(userRequestDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    /**
     * Endpoint para obtener un usuario por su ID.
     *
     * @param id el ID del usuario a buscar.
     * @return ResponseEntity que contiene el usuario encontrado y un código de estado HTTP.
     *         Si no se encuentra el usuario, se lanza una excepción con un código de estado NOT_FOUND.
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable UUID id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    /**
     * Endpoint para obtener todos los usuarios registrados en el sistema.
     *
     * @return ResponseEntity que contiene una lista de todos los usuarios y un código de estado HTTP.
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    /**
     * Endpoint para actualizar la información de un usuario existente.
     *
     * @param id el ID del usuario a actualizar.
     * @param userRequestDto DTO que contiene la nueva información del usuario.
     * @return ResponseEntity que contiene el usuario actualizado y un código de estado HTTP.
     *         Si no se encuentra el usuario, se lanza una excepción con un código de estado NOT_FOUND.
     */
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable UUID id, @RequestBody UserRequestDto userRequestDto) {
        User updatedUser = userService.updateUser(id, userRequestDto);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Endpoint para eliminar un usuario del sistema por su ID.
     *
     * @param id el ID del usuario a eliminar.
     * @return ResponseEntity que indica que la operación se ha completado sin contenido.
     *         Si no se encuentra el usuario, se lanza una excepción con un código de estado NOT_FOUND.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
