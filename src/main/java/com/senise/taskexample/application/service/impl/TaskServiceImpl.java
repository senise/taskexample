package com.senise.taskexample.application.service.impl;

import com.senise.taskexample.application.dto.request.TaskRequestDTO;
import com.senise.taskexample.application.dto.response.TaskResponseDTO;
import com.senise.taskexample.application.service.TaskService;
import com.senise.taskexample.domain.usecase.task.*;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final CreateTaskUseCase createTaskUseCase;
    private final GetAllTasksUseCase getAllTasksUseCase;
    private final GetTaskByIdUseCase getTaskByIdUseCase;
    private final UpdateTaskUseCase updateTaskUseCase;
    private final DeleteTaskUseCase deleteTaskUseCase;
    private final GetTasksByUserIdUseCase getTasksByUserIdUseCase;
    private final SearchTasksUseCase searchTasksUseCase;
    private final GetTasksCreatedInPeriodUseCase getTasksCreatedInPeriodUseCase;

    /**
     * Crea una nueva tarea.
     */
    public TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO, Authentication authentication) {
        return createTaskUseCase.execute(taskRequestDTO, authentication);
    }

    /**
     * Obtiene la lista de todas las tareas.
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")  // Solo para administradores
    public List<TaskResponseDTO> getAllTasks() {
        return getAllTasksUseCase.execute();
    }

    /**
     * Obtiene el detalle de una tarea por su ID.
     */
    public TaskResponseDTO getTaskById(Long id, Authentication authentication) {
        return getTaskByIdUseCase.execute(id, authentication);
    }

    /**
     * Actualiza una tarea por su ID.
     */
    public TaskResponseDTO updateTask(Long id, TaskRequestDTO taskRequestDTO, Authentication authentication) {
        return updateTaskUseCase.execute(id, taskRequestDTO, authentication);
    }

    /**
     * Elimina una tarea por su ID.
     */
    public void deleteTask(Long id, Authentication authentication) {
        deleteTaskUseCase.execute(id, authentication);
    }

    /**
     * Busca tareas pertenecen a un usuario.
     */
    @Override
    public List<TaskResponseDTO> getTasksByUserId(Long userId, Authentication authentication) {
        return getTasksByUserIdUseCase.execute(userId, authentication);
    }

    /**
     * Busca tareas por criterios.
     */
    @Override
    public List<TaskResponseDTO> searchTasks(String title, String description, Boolean completed, Authentication authentication) {
        return searchTasksUseCase.execute(title, description, completed, authentication);

    }

    /**
     * Busca tareas creadas dentro de un período de tiempo.
     */
    @Override
    public List<TaskResponseDTO> getTasksCreatedInPeriod(LocalDateTime startDate, LocalDateTime endDate, Authentication authentication) {
        return getTasksCreatedInPeriodUseCase.execute(startDate, endDate, authentication);
    }

}
