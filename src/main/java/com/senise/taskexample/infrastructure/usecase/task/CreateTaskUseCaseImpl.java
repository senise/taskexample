package com.senise.taskexample.infrastructure.usecase.task;

import com.senise.taskexample.application.dto.request.TaskRequestDTO;
import com.senise.taskexample.application.dto.response.TaskResponseDTO;
import com.senise.taskexample.application.mapper.TaskMapper;
import com.senise.taskexample.application.security.SecurityService;
import com.senise.taskexample.domain.entity.Task;
import com.senise.taskexample.domain.entity.User;
import com.senise.taskexample.domain.usecase.task.CreateTaskUseCase;
import com.senise.taskexample.domain.usecase.user.GetUserByIdCheckUseCase;
import com.senise.taskexample.infrastructure.respository.TaskRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Transactional
public class CreateTaskUseCaseImpl implements CreateTaskUseCase {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final SecurityService securityService;
    private final GetUserByIdCheckUseCase getUserByIdCheckUseCase;

    @Override
    public TaskResponseDTO execute(TaskRequestDTO taskRequestDTO, Authentication authentication) {
        User user = getUserByIdCheckUseCase.execute(taskRequestDTO.getUserId());
        // Verificar permisos de acceso
        securityService.verifyAccess(authentication, user.getEmail());

        Task task = taskMapper.toEntity(taskRequestDTO, user);
        taskRepository.save(task);
        return taskMapper.toResponseDTO(task);
    }
}
