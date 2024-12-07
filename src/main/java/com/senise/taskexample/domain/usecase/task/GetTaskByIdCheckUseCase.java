package com.senise.taskexample.domain.usecase.task;

import com.senise.taskexample.application.dto.response.TaskResponseDTO;
import com.senise.taskexample.domain.entity.Task;

import java.util.List;

public interface GetTaskByIdCheckUseCase {
    Task execute(Long taskId);
}
