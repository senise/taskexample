package com.senise.taskexample.domain.usecase.task;

import com.senise.taskexample.application.dto.request.TaskRequestDTO;
import com.senise.taskexample.application.dto.response.TaskResponseDTO;
import com.senise.taskexample.domain.entity.User;
import org.springframework.security.core.Authentication;

// Caso de uso para crear una tarea
public interface CreateTaskUseCase {
    TaskResponseDTO execute(TaskRequestDTO taskRequestDTO, Authentication authentication);
}