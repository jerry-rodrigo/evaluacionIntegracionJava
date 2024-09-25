package com.desafioTecnico.services;

import com.desafioTecnico.dtos.UserRequestDto;
import com.desafioTecnico.models.User;

import java.util.List;
import java.util.UUID;

/**
 * Interfaz que define los servicios relacionados con la gestión de usuarios.
 * Proporciona métodos para registrar y gestionar la información de los usuarios.
 */
public interface UserService {

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * @param userRequestDto objeto que contiene la información del usuario a registrar.
     * @return el objeto {@link User} que representa al usuario registrado.
     * @throws IllegalArgumentException si el correo electrónico ya está registrado
     *         o si hay problemas con la validación de la contraseña.
     */
    User registerUser(UserRequestDto userRequestDto);

    /**
     * Busca un usuario por su ID.
     *
     * @param id el ID del usuario a buscar.
     * @return el objeto {@link User} que representa al usuario encontrado.
     * @throws RuntimeException si no se encuentra un usuario con el ID especificado.
     */
    User findById(UUID id);

    /**
     * Obtiene una lista de todos los usuarios registrados en el sistema.
     *
     * @return una lista de objetos {@link User} que representan a todos los usuarios.
     */
    List<User> findAll();

    /**
     * Actualiza la información de un usuario existente.
     *
     * @param id el ID del usuario a actualizar.
     * @param userRequestDto objeto que contiene la nueva información del usuario.
     * @return el objeto {@link User} que representa al usuario actualizado.
     * @throws RuntimeException si no se encuentra un usuario con el ID especificado.
     */
    User updateUser(UUID id, UserRequestDto userRequestDto);

    /**
     * Elimina un usuario del sistema por su ID.
     *
     * @param id el ID del usuario a eliminar.
     * @throws RuntimeException si no se encuentra un usuario con el ID especificado.
     */
    void deleteUser(UUID id);
}
