package com.desafioTecnico.services.impl;

import com.desafioTecnico.dtos.UserRequestDto;
import com.desafioTecnico.exceptions.EmailValidationException;
import com.desafioTecnico.exceptions.PasswordValidationException;
import com.desafioTecnico.exceptions.UserNotFoundException;
import com.desafioTecnico.models.Phone;
import com.desafioTecnico.models.User;
import com.desafioTecnico.repositories.UserRepository;
import com.desafioTecnico.services.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Implementación del servicio de usuario que gestiona el registro y la validación de usuarios.
 * Proporciona métodos para registrar nuevos usuarios, buscar, actualizar y eliminar usuarios.
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Value("${user.password.regex}")
    private String passwordRegex;

    @Value("${user.email.regex}")
    private String emailRegex;

    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    /**
     * Constructor de UserServiceImpl.
     *
     * @param userRepository Repositorio de usuarios utilizado para acceder a los datos de los usuarios.
     */
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * @param userRequestDto DTO que contiene la información del usuario a registrar.
     * @return El usuario registrado con la información correspondiente.
     * @throws EmailValidationException Si el correo ya está registrado en el sistema.
     * @throws PasswordValidationException Si la contraseña no cumple con los requisitos de seguridad.
     */
    @Override
    @Transactional
    public User registerUser(UserRequestDto userRequestDto) {

        validateEmail(userRequestDto.getEmail());
        validatePassword(userRequestDto.getPassword());

        User user = new User();
        user.setName(userRequestDto.getName());
        user.setEmail(userRequestDto.getEmail());

        String hashedPassword = BCrypt.hashpw(userRequestDto.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPassword);

        List<Phone> phones = userRequestDto.getPhones().stream()
                .map(phoneDto -> {
                    Phone phone = new Phone();
                    phone.setNumber(phoneDto.getNumber());
                    phone.setCityCode(phoneDto.getCitycode());
                    phone.setContryCode(phoneDto.getContrycode());
                    return phone;
                }).collect(Collectors.toList());
        user.setPhones(phones);
        user.setCreated(LocalDateTime.now());
        user.setModified(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());
        user.setIsActive(true);

        String token = generateToken(user.getEmail());
        user.setToken(token);

        return userRepository.save(user);
    }

    /**
     * Valida la contraseña de acuerdo a una expresión regular.
     *
     * @param password La contraseña a validar.
     * @throws PasswordValidationException Si la contraseña no cumple con los requisitos.
     */
    private void validatePassword(String password) {
        if (!Pattern.matches(passwordRegex, password)) {
            throw new PasswordValidationException("La contraseña debe tener al menos 8 caracteres, " +
                    "incluir al menos una letra mayúscula, una letra minúscula, " +
                    "un número y un carácter especial.");
        }
    }

    /**
     * Valida si el correo tiene un formato válido.
     *
     * @param email El correo a validar.
     * @throws EmailValidationException Si el formato del correo no es válido.
     */
    private void validateEmail(String email) {
        if (!Pattern.matches(emailRegex, email)) {
            throw new EmailValidationException("El correo no tiene un formato válido.");
        }
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            throw new EmailValidationException("El correo ya registrado");
        }
    }

    /**
     * Genera un token JWT para el usuario.
     *
     * @param email El correo del usuario.
     * @return El token JWT generado.
     */
    private String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date())
                .signWith(secretKey)
                .compact();
    }

    /**
     * Busca un usuario por su ID.
     *
     * @param id El ID del usuario a buscar.
     * @return El usuario encontrado.
     * @throws RuntimeException Si el usuario no se encuentra.
     */
    @Override
    public User findById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    /**
     * Encuentra todos los usuarios registrados en el sistema.
     *
     * @return Una lista de todos los usuarios.
     */
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Actualiza la información de un usuario existente.
     *
     * @param id              El ID del usuario a actualizar.
     * @param userRequestDto  DTO que contiene la nueva información del usuario.
     * @return El usuario actualizado.
     */
    @Override
    @Transactional
    public User updateUser(UUID id, UserRequestDto userRequestDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado con ID: " + id));

        if (userRequestDto.getName() != null && !userRequestDto.getName().isEmpty()) {
            user.setName(userRequestDto.getName());
        }

        if (!userRequestDto.getEmail().equals(user.getEmail())) {
            validateEmail(userRequestDto.getEmail());
            user.setEmail(userRequestDto.getEmail());
        }

        if (userRequestDto.getPassword() != null && !userRequestDto.getPassword().isEmpty()) {
            validatePassword(userRequestDto.getPassword());
            String hashedPassword = BCrypt.hashpw(userRequestDto.getPassword(), BCrypt.gensalt());
            user.setPassword(hashedPassword);
        }

        if (userRequestDto.getActive() != null) {
            user.setIsActive(userRequestDto.getActive());
        }

        user.setCreated(user.getCreated());
        user.setModified(LocalDateTime.now());
        user.setLastLogin(user.getLastLogin());

        if (userRequestDto.getPhones() != null) {
            List<Phone> existingPhones = user.getPhones();

            List<Phone> updatedPhones = userRequestDto.getPhones().stream()
                    .map(phoneDto -> {
                        Phone phone = existingPhones.stream()
                                .filter(p -> p.getNumber().equals(phoneDto.getNumber()))
                                .findFirst()
                                .orElse(new Phone());
                        phone.setNumber(phoneDto.getNumber());
                        phone.setCityCode(phoneDto.getCitycode());
                        phone.setContryCode(phoneDto.getContrycode());

                        return phone;
                    }).collect(Collectors.toList());

            user.setPhones(updatedPhones);
        }

        return userRepository.save(user);
    }

    /**
     * Elimina un usuario por su ID.
     *
     * @param id El ID del usuario a eliminar.
     */
    @Override
    @Transactional
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }
}
