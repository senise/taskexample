package com.senise.taskexample.infrastructure.usecase.task;

import com.senise.taskexample.application.dto.response.TaskResponseDTO;
import com.senise.taskexample.application.mapper.TaskMapper;
import com.senise.taskexample.domain.entity.Task;
import com.senise.taskexample.domain.usecase.task.GetAllTasksUseCase;
import com.senise.taskexample.infrastructure.respository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetAllTasksUseCaseImpl implements GetAllTasksUseCase {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Override
    public List<TaskResponseDTO> execute() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
                .map(taskMapper::toResponseDTO)
                .toList();
    }
}
