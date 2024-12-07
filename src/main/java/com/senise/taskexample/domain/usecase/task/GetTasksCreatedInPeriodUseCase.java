package com.senise.taskexample.domain.usecase.task;

import com.senise.taskexample.application.dto.response.TaskResponseDTO;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.List;

public interface GetTasksCreatedInPeriodUseCase {
    List<TaskResponseDTO> execute(LocalDateTime startDate, LocalDateTime endDate, Authentication authentication);
}
