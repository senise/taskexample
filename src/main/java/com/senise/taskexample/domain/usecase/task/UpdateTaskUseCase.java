package com.senise.taskexample.domain.usecase.task;

import com.senise.taskexample.application.dto.request.TaskRequestDTO;
import com.senise.taskexample.application.dto.response.TaskResponseDTO;
import org.springframework.security.core.Authentication;

// Caso de uso para actualizar una tarea
public interface UpdateTaskUseCase {
    TaskResponseDTO execute(Long id, TaskRequestDTO taskRequestDTO, Authentication authentication);
}