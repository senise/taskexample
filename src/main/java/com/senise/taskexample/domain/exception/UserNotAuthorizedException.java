package com.senise.taskexample.domain.exception;

public class UserNotAuthorizedException extends RuntimeException {

    public UserNotAuthorizedException(String message) {
        super(message); // Pasamos el mensaje de error a la clase base RuntimeException
    }

    public UserNotAuthorizedException(String message, Throwable cause) {
        super(message, cause); // Permite encadenar una causa a la excepción
    }
}