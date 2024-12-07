package com.senise.taskexample.application.exception;

import com.senise.taskexample.domain.exception.TaskNotFoundException;
import com.senise.taskexample.domain.exception.UserNotAuthorizedException;
import com.senise.taskexample.domain.exception.UserNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Manejar UserNotFoundException
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFound(UserNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // Manejar TaskNotFoundException
    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<String> handleTaskNotFound(TaskNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // Manejador de violaciones de integridad de datos
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        // Analizar el mensaje de la excepción para identificar el campo violado
        String message = ex.getMessage().toLowerCase();

        if (message.contains("email")) {
            return new ResponseEntity<>("El correo electrónico ya está registrado.", HttpStatus.BAD_REQUEST);
        } else if (message.contains("id")) {
            return new ResponseEntity<>("El ID ya está en uso.", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>("Violación de restricción de integridad.", HttpStatus.BAD_REQUEST);
        }
    }

    @ExceptionHandler(UserNotAuthorizedException.class)
    public ResponseEntity<String> handleUserNotAuthorizedException(UserNotAuthorizedException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN); // Respondemos con código 403 y el mensaje de la excepción
    }

    // Manejar cualquier otra excepción
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGlobalException(Exception ex) {
        return new ResponseEntity<>("Ocurrió un error inesperado: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Maneja excepciones de acceso denegado (por roles insuficientes).
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException ex) {
        String errorMessage = "Acceso denegado. No tienes permisos suficientes para acceder a este recurso.";
        return new ResponseEntity<>(errorMessage, HttpStatus.FORBIDDEN);  // 403 Forbidden
    }
}