package com.senise.taskexample.infrastructure.usecase.task;

import com.senise.taskexample.application.dto.response.TaskResponseDTO;
import com.senise.taskexample.application.mapper.TaskMapper;
import com.senise.taskexample.application.security.SecurityService;
import com.senise.taskexample.domain.entity.Task;
import com.senise.taskexample.domain.entity.User;
import com.senise.taskexample.domain.usecase.task.GetTasksCreatedInPeriodUseCase;
import com.senise.taskexample.domain.usecase.user.GetUserByIdCheckUseCase;
import com.senise.taskexample.infrastructure.respository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GetTasksCreatedInPeriodUseCaseImpl implements GetTasksCreatedInPeriodUseCase {
    private final SecurityService securityService;
    private final GetUserByIdCheckUseCase getUserByIdCheckUseCase;
    private final TaskMapper taskMapper;
    private final TaskRepository taskRepository;

    @Override
    public List<TaskResponseDTO> execute(LocalDateTime startDate, LocalDateTime endDate, Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal(); // Obtener el usuario autenticado

        // Verificar si el usuario tiene rol de administrador
        boolean isAdmin = securityService.isAdmin(authentication);

        List<Task> tasks;

        if (isAdmin) {
            // Administrador: puede ver todas las tareas dentro del rango de fechas
            tasks = taskRepository.findByCreatedAtBetween(startDate, endDate);
        } else {
            // Usuario normal: solo puede ver sus propias tareas dentro del rango de fechas
            tasks = taskRepository.findByUserAndCreatedAtBetween(currentUser, startDate, endDate);
        }

        // Convertimos las tareas a DTOs
        return tasks.stream()
                .map(taskMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
