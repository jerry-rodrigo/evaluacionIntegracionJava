package com.desafioTecnico.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * DTO (Data Transfer Object) que representa la solicitud para registrar un usuario.
 * Incluye la información del usuario y sus teléfonos.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @NotBlank(message = "El correo es obligatorio")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;

    private Boolean active;

    @Valid
    private List<PhoneDto> phones;

}
