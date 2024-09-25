package com.desafioTecnico.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

/**
 * Representa un número de teléfono asociado a un usuario.
 * Esta entidad se almacena en la base de datos y contiene la información necesaria
 * sobre el número de teléfono, incluyendo el código de ciudad y el código del país.
 */
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "phones")
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;

    @Column(nullable = false)
    private String number;

    @Column(nullable = false)
    private String cityCode;

    @Column(nullable = false)
    private String contryCode;
}
