package com.senise.taskexample.infrastructure.usecase.task;

import com.senise.taskexample.application.dto.response.TaskResponseDTO;
import com.senise.taskexample.application.mapper.TaskMapper;
import com.senise.taskexample.application.security.SecurityService;
import com.senise.taskexample.domain.entity.Task;
import com.senise.taskexample.domain.entity.User;
import com.senise.taskexample.domain.usecase.task.GetTaskByIdCheckUseCase;
import com.senise.taskexample.domain.usecase.task.GetTasksByUserIdUseCase;
import com.senise.taskexample.domain.usecase.user.GetUserByIdCheckUseCase;
import com.senise.taskexample.infrastructure.respository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GetTasksByUserIdUseCaseImpl implements GetTasksByUserIdUseCase {

    private final SecurityService securityService;
    private final GetUserByIdCheckUseCase getUserByIdCheckUseCase;
    private final TaskMapper taskMapper;
    private final TaskRepository taskRepository;

    @Override
    public List<TaskResponseDTO> execute(Long userId, Authentication authentication) {
        User user = getUserByIdCheckUseCase.execute(userId);

        // Verificar permisos de acceso
        securityService.verifyAccess(authentication, user.getEmail());

        List<Task> tasks = taskRepository.findByUserId(userId);
        return tasks.stream()
                .map(taskMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
