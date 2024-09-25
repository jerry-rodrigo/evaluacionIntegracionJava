package com.desafioTecnico.repositories;

import com.desafioTecnico.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Interfaz de repositorio para la entidad {@link User}.
 * Proporciona métodos para acceder y manipular datos de usuarios en la base de datos.
 */
public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * Busca un usuario por su dirección de correo electrónico.
     *
     * @param email la dirección de correo electrónico del usuario a buscar.
     * @return un {@link Optional} que contiene el usuario encontrado,
     *         o vacío si no se encuentra ningún usuario con el correo especificado.
     */
    Optional<User> findByEmail(String email);

    /**
     * Elimina un usuario por su ID.
     *
     * @param id El ID del usuario a eliminar.
     */
    void deleteById(UUID id);

    /**
     * Busca un usuario por su ID.
     *
     * @param id El ID del usuario a buscar.
     * @return un {@link Optional} que contiene el usuario encontrado,
     *         o vacío si no se encuentra ningún usuario con el ID especificado.
     */
    Optional<User> findById(UUID id);
}
