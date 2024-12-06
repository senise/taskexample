package com.senise.taskexample.application.service.impl;

import com.senise.taskexample.application.dto.request.TaskRequestDTO;
import com.senise.taskexample.application.dto.response.TaskResponseDTO;
import com.senise.taskexample.application.mapper.TaskMapper;
import com.senise.taskexample.application.security.SecurityService;
import com.senise.taskexample.application.service.TaskService;
import com.senise.taskexample.domain.entity.Task;
import com.senise.taskexample.domain.entity.User;
import com.senise.taskexample.domain.exception.TaskNotFoundException;
import com.senise.taskexample.domain.exception.UserNotAuthorizedException;
import com.senise.taskexample.domain.exception.UserNotFoundException;
import com.senise.taskexample.infrastructure.respository.TaskRepository;
import com.senise.taskexample.infrastructure.respository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;
    private final SecurityService securityService;

    /**
     * Crea una nueva tarea.
     */
    public TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO, Authentication authentication) {
        User user = getUserById(taskRequestDTO.getUserId());

        // Verificar permisos de acceso
        verifyAccess(authentication, user.getEmail());

        Task task = taskMapper.toEntity(taskRequestDTO, user);
        taskRepository.save(task);
        return taskMapper.toResponseDTO(task);
    }

    /**
     * Obtiene la lista de todas las tareas.
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")  // Solo para administradores
    public List<TaskResponseDTO> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
                .map(taskMapper::toResponseDTO)
                .toList();
    }

    /**
     * Obtiene el detalle de una tarea por su ID.
     */
    public TaskResponseDTO getTaskById(Long id, Authentication authentication) {
        Task task = getTaskById(id);
        User user = getUserById(task.getUser().getId());

        // Verificar permisos de acceso
        verifyAccess(authentication, user.getEmail());

        return taskMapper.toResponseDTO(task);
    }

    /**
     * Actualiza una tarea por su ID.
     */
    public TaskResponseDTO updateTask(Long id, TaskRequestDTO taskRequestDTO, Authentication authentication) {
        Task existingTask = getTaskById(id);
        User user = getUserById(taskRequestDTO.getUserId());

        // Verificar permisos de acceso
        verifyAccess(authentication, user.getEmail());

        existingTask.setTitle(taskRequestDTO.getTitle());
        existingTask.setDescription(taskRequestDTO.getDescription());
        existingTask.setCompleted(taskRequestDTO.isCompleted());
        existingTask.setUser(user);

        taskRepository.save(existingTask);

        return taskMapper.toResponseDTO(existingTask);
    }

    /**
     * Elimina una tarea por su ID.
     */
    public void deleteTask(Long id, Authentication authentication) {
        Task task = getTaskById(id);
        User user = task.getUser();

        // Verificar permisos de acceso
        verifyAccess(authentication, user.getEmail());

        taskRepository.delete(task);
    }

    @Override
    public List<TaskResponseDTO> getTasksByUserId(Long userId, Authentication authentication) {
        User user = getUserById(userId);

        // Verificar permisos de acceso
        verifyAccess(authentication, user.getEmail());

        List<Task> tasks = taskRepository.findByUserId(userId);
        return tasks.stream()
                .map(taskMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Método privado para obtener un usuario por su ID y lanzar excepción si no se encuentra.
     */
    private User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));
    }

    /**
     * Método privado para obtener una tarea por su ID y lanzar excepción si no se encuentra.
     */
    private Task getTaskById(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Tarea no encontrada"));
    }

    /**
     * Verifica si el usuario tiene permisos para acceder a un recurso.
     */
    private void verifyAccess(Authentication authentication, String userEmail) {
        if (!securityService.canAccessResource(authentication, userEmail)) {
            throw new UserNotAuthorizedException("No tienes permisos para acceder a este recurso.");
        }
    }
}
