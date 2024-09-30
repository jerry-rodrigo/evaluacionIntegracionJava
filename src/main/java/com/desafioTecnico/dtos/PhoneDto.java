package com.desafioTecnico.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * DTO (Data Transfer Object) que representa la información de un número de teléfono.
 * Se utiliza para transferir datos entre el cliente y el servidor.
 */
@Data
@AllArgsConstructor
public class PhoneDto {

    @NotNull(message = "El número de teléfono no puede ser nulo")
    @NotEmpty(message = "El número de teléfono no puede estar vacío")
    private String number;

    @NotNull(message = "El código de ciudad no puede ser nulo")
    @NotEmpty(message = "El código de ciudad no puede estar vacío")
    private String citycode;

    @NotNull(message = "El código de país no puede ser nulo")
    @NotEmpty(message = "El código de país no puede estar vacío")
    private String contrycode;

}
