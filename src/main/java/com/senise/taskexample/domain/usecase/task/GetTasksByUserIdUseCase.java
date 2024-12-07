package com.senise.taskexample.domain.usecase.task;

import com.senise.taskexample.application.dto.response.TaskResponseDTO;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface GetTasksByUserIdUseCase {
    List<TaskResponseDTO> execute(Long userId, Authentication authentication);
}
