package com.senise.taskexample.infrastructure.usecase.task;

import com.senise.taskexample.application.dto.request.TaskRequestDTO;
import com.senise.taskexample.application.dto.response.TaskResponseDTO;
import com.senise.taskexample.application.mapper.TaskMapper;
import com.senise.taskexample.application.security.SecurityService;
import com.senise.taskexample.domain.entity.Task;
import com.senise.taskexample.domain.entity.User;
import com.senise.taskexample.domain.usecase.task.GetTaskByIdCheckUseCase;
import com.senise.taskexample.domain.usecase.task.UpdateTaskUseCase;
import com.senise.taskexample.domain.usecase.user.GetUserByIdCheckUseCase;
import com.senise.taskexample.infrastructure.respository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UpdateTaskUseCaseImpl implements UpdateTaskUseCase {
    private final GetTaskByIdCheckUseCase getTaskByIdCheckUseCase;
    private final GetUserByIdCheckUseCase getUserByIdCheckUseCase;
    private final SecurityService securityService;
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Override
    public TaskResponseDTO execute(Long id, TaskRequestDTO taskRequestDTO, Authentication authentication) {
        Task existingTask = getTaskByIdCheckUseCase.execute(id);
        User user = getUserByIdCheckUseCase.execute(taskRequestDTO.getUserId());

        // Verificar permisos de acceso
        securityService.verifyAccess(authentication, user.getEmail());

        existingTask.setTitle(taskRequestDTO.getTitle());
        existingTask.setDescription(taskRequestDTO.getDescription());
        existingTask.setCompleted(taskRequestDTO.isCompleted());
        existingTask.setUser(user);

        taskRepository.save(existingTask);

        return taskMapper.toResponseDTO(existingTask);
    }
}
