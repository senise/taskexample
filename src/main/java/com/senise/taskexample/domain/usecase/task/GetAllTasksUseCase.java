package com.senise.taskexample.domain.usecase.task;

import com.senise.taskexample.application.dto.response.TaskResponseDTO;
import org.springframework.security.core.Authentication;

import java.util.List;

// Caso de uso para obtener todas las tareas
public interface GetAllTasksUseCase {
    List<TaskResponseDTO> execute();
}
