package com.senise.taskexample.application.exception;

import com.senise.taskexample.application.dto.response.ErrorResponseDTO;
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
    public ResponseEntity<ErrorResponseDTO> handleUserNotFound(UserNotFoundException ex) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(ex.getMessage(),HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.NOT_FOUND);
    }

    // Manejar TaskNotFoundException
    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleTaskNotFound(TaskNotFoundException ex) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(ex.getMessage(),HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.NOT_FOUND);
    }

    // Manejador de violaciones de integridad de datos
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDTO> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        // Analizar el mensaje de la excepción para identificar el campo violado
        String message = ex.getMessage().toLowerCase();
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(ex.getMessage(),HttpStatus.NOT_FOUND.value());

        if (message.contains("email")) {
            errorResponseDTO.setMessage("El correo electrónico ya está registrado.");
        } else if (message.contains("id")) {
            errorResponseDTO.setMessage("El ID ya está en uso.");
        } else {
            errorResponseDTO.setMessage("Violación de restricción de integridad.");
        }

        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(UserNotAuthorizedException.class)
    public ResponseEntity<ErrorResponseDTO> handleUserNotAuthorizedException(UserNotAuthorizedException ex) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(ex.getMessage(),HttpStatus.FORBIDDEN.value());
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.FORBIDDEN); // Respondemos con código 403 y el mensaje de la excepción
    }

    // Manejar cualquier otra excepción
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGlobalException(Exception ex) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO("Ocurrió un error inesperado: " + ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value());

        return new ResponseEntity<>(errorResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Maneja excepciones de acceso denegado (por roles insuficientes).
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponseDTO> handleAccessDeniedException(AccessDeniedException ex) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO("Acceso denegado. No tienes permisos suficientes para acceder a este recurso.",HttpStatus.FORBIDDEN.value());
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.FORBIDDEN);  // 403 Forbidden
    }
}