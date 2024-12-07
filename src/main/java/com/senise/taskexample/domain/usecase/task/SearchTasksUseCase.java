package com.senise.taskexample.domain.usecase.task;

import com.senise.taskexample.application.dto.response.TaskResponseDTO;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface SearchTasksUseCase {
    List<TaskResponseDTO> execute(String title, String description, Boolean completed, Authentication authentication);
}
