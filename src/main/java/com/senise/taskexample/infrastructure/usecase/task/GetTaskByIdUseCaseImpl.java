package com.senise.taskexample.infrastructure.usecase.task;


import com.senise.taskexample.application.dto.response.TaskResponseDTO;
import com.senise.taskexample.application.mapper.TaskMapper;
import com.senise.taskexample.application.security.SecurityService;
import com.senise.taskexample.domain.entity.Task;
import com.senise.taskexample.domain.entity.User;
import com.senise.taskexample.domain.usecase.task.GetTaskByIdCheckUseCase;
import com.senise.taskexample.domain.usecase.task.GetTaskByIdUseCase;
import com.senise.taskexample.domain.usecase.user.GetUserByIdCheckUseCase;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetTaskByIdUseCaseImpl implements GetTaskByIdUseCase {

    private final SecurityService securityService;
    private final GetTaskByIdCheckUseCase getTaskByIdCheckUseCase;
    private final GetUserByIdCheckUseCase getUserByIdCheckUseCase;
    private final TaskMapper taskMapper;

    @Override
    public TaskResponseDTO execute(Long id, Authentication authentication) {
        Task task = getTaskByIdCheckUseCase.execute(id);
        User user = getUserByIdCheckUseCase.execute(task.getUser().getId());

        // Verificar permisos de acceso
        securityService.verifyAccess(authentication, user.getEmail());

        return taskMapper.toResponseDTO(task);
    }
}
