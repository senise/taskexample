package com.senise.taskexample.domain.usecase.task;

import com.senise.taskexample.application.dto.response.TaskResponseDTO;
import org.springframework.security.core.Authentication;

// Caso de uso para obtener tarea por ID
public interface GetTaskByIdUseCase {
    TaskResponseDTO execute(Long id, Authentication authentication);
}
