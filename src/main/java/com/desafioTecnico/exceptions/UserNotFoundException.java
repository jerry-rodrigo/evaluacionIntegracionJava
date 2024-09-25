package com.desafioTecnico.exceptions;

/**
 * Excepción que indica que un usuario no fue encontrado.
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
