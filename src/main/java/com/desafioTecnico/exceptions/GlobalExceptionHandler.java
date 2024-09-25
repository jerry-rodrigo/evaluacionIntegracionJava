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
 * Captura y maneja excepciones específicas, proporcionando respuestas adecuadas.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja excepciones de validación de argumentos de método.
     *
     * @param ex la excepción de validación que se lanzó
     * @return un ResponseEntity que contiene un mapa de errores de validación y un estado HTTP 400
     */
    @Operation(summary = "Manejo de errores de validación")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Errores de validación de campos")
    })
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Maneja excepciones relacionadas con la validación de contraseñas.
     *
     * @param ex la excepción de validación de contraseña que se lanzó
     * @return un ResponseEntity que contiene el mensaje de error y un estado HTTP 400
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
     * Maneja excepciones relacionadas con la validación de correos electrónicos.
     *
     * @param ex la excepción de validación de correo electrónico que se lanzó
     * @return un ResponseEntity que contiene el mensaje de error y un estado HTTP 400
     */
    @Operation(summary = "Manejo de errores de validación de correo electrónico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "El correo electrónico ya está registrado")
    })
    @ExceptionHandler(EmailValidationException.class)
    public ResponseEntity<String> handleEmailValidationException(EmailValidationException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    /**
     * Maneja excepciones relacionadas con la validación de usuarios existentes.
     *
     * @param ex la excepción de validación de usuarios existentes que se lanzó
     * @return un ResponseEntity que contiene el mensaje de error y un estado HTTP 400
     */
    @Operation(summary = "Manejo de errores de validación de usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "El usuario no existe")
    })
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }
}
