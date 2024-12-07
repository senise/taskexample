package com.senise.taskexample.infrastructure.usecase.task;

import com.senise.taskexample.domain.entity.Task;
import com.senise.taskexample.domain.exception.TaskNotFoundException;
import com.senise.taskexample.domain.usecase.task.GetTaskByIdCheckUseCase;
import com.senise.taskexample.infrastructure.respository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetTaskByIdCheckUseCaseImpl  implements GetTaskByIdCheckUseCase {

    private final TaskRepository taskRepository;

    @Override
    public Task execute(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Tarea no encontrada"));
    }
}
