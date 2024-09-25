package com.desafioTecnico.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * DTO (Data Transfer Object) que representa la información de un número de teléfono.
 * Se utiliza para transferir datos entre el cliente y el servidor.
 */
@Data
@AllArgsConstructor
public class PhoneDto {

    @NotBlank(message = "El número es obligatorio")
    private String number;

    @NotBlank(message = "El código de ciudad es obligatorio")
    private String citycode;

    @NotBlank(message = "El código de país es obligatorio")
    private String contrycode;

}
