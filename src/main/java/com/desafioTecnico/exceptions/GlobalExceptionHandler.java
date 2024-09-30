package com.desafioTecnico.exceptions;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Manejador global de excepciones para la aplicación.
 * Captura y maneja excepciones específicas relacionadas con la validación de datos de usuario
 * y otras excepciones de validación general, proporcionando respuestas HTTP adecuadas.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja las excepciones de validación de argumentos de método, típicamente lanzadas
     * cuando se violan las restricciones de validación de anotaciones en los DTO.
     *
     * @param ex la excepción de validación que se lanzó
     * @return un ResponseEntity que contiene un mapa con los errores de validación de campos y un estado HTTP 400 (BAD REQUEST)
     */
    @Operation(summary = "Manejo de errores de validación")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Errores de validación de campos")
    })
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
                String fieldName = error.getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);

        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Maneja las excepciones relacionadas con la validación de contraseñas.
     * Esto ocurre cuando la contraseña no cumple con los requisitos establecidos, como longitud o complejidad.
     *
     * @param ex la excepción de validación de contraseña lanzada
     * @return un ResponseEntity que contiene el mensaje de error y un estado HTTP 400 (BAD REQUEST)
     */
    @Operation(summary = "Manejo de errores de validación de contraseña")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "La contraseña no cumple con los requisitos")
    })
    @ExceptionHandler(PasswordValidationException.class)
    public ResponseEntity<String> handlePasswordValidationException(PasswordValidationException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    /**
     * Maneja las excepciones relacionadas con la validación de correos electrónicos.
     * Esto ocurre cuando el formato del correo es inválido o el correo ya está registrado.
     *
     * @param ex la excepción de validación de correo electrónico lanzada
     * @return un ResponseEntity que contiene el mensaje de error y un estado HTTP 400 (BAD REQUEST)
     */
    @Operation(summary = "Manejo de errores de validación de correo electrónico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "El correo electrónico ya está registrado o es inválido")
    })
    @ExceptionHandler(EmailValidationException.class)
    public ResponseEntity<String> handleEmailValidationException(EmailValidationException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    /**
     * Maneja las excepciones relacionadas con la validación de usuarios no encontrados.
     * Esto ocurre cuando se intenta buscar un usuario que no existe en la base de datos.
     *
     * @param ex la excepción de usuario no encontrado lanzada
     * @return un ResponseEntity que contiene el mensaje de error y un estado HTTP 404 (NOT FOUND)
     */
    @Operation(summary = "Manejo de errores de validación de usuario no encontrado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "El usuario no existe")
    })
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }
}
