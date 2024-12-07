package com.senise.taskexample.domain.usecase.task;

import org.springframework.security.core.Authentication;

// Caso de uso para eliminar una tarea
public interface DeleteTaskUseCase {
    void execute(Long id, Authentication authentication);
}