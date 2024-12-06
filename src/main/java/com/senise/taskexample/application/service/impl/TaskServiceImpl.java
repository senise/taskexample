package com.senise.taskexample.application.service.impl;

import com.senise.taskexample.application.dto.request.TaskRequestDTO;
import com.senise.taskexample.application.dto.response.TaskResponseDTO;
import com.senise.taskexample.application.mapper.TaskMapper;
import com.senise.taskexample.application.service.TaskService;
import com.senise.taskexample.domain.entity.Task;
import com.senise.taskexample.domain.entity.User;
import com.senise.taskexample.domain.exception.TaskNotFoundException;
import com.senise.taskexample.domain.exception.UserNotAuthorizedException;
import com.senise.taskexample.domain.exception.UserNotFoundException;
import com.senise.taskexample.infrastructure.respository.TaskRepository;
import com.senise.taskexample.infrastructure.respository.UserRepository;
import lombok.AllArgsConstructor;
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

    /**
     * Crea una nueva tarea.
     */
    public TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO, Authentication authentication) {
        // Obtener el usuario de la base de datos por su ID
        User user = userRepository.findById(taskRequestDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));

        // Verificar si el usuario logueado es el mismo que el de la tarea o si es un administrador
        if (user.getEmail().equals(authentication.getName()) || isAdmin(authentication)) {
            // Crear la tarea si el usuario tiene los permisos adecuados
            Task task = taskMapper.toEntity(taskRequestDTO, user);
            taskRepository.save(task);
            return taskMapper.toResponseDTO(task);
        } else {
            // Lanzar la excepción si no tiene los permisos adecuados
            throw new UserNotAuthorizedException("No tienes permisos para crear una tarea para este usuario.");
        }
    }

    /**
     * Obtiene la lista de todas las tareas.
     */
    public List<TaskResponseDTO> getAllTasks(/*String userMail*/) {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
                .map(taskMapper::toResponseDTO)
                .toList();
    }

    /**
     * Obtiene el detalle de una tarea por su ID.
     */
    public TaskResponseDTO getTaskById(Long id, String userMail) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Tarea no encontrada"));

        return taskMapper.toResponseDTO(task);
    }

    /**
     * Actualiza una tarea por su ID.
     */
    public TaskResponseDTO updateTask(Long id, TaskRequestDTO taskRequestDTO, String userMail) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Tarea no encontrada"));

        User user = userRepository.findById(taskRequestDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));

        // Actualizar los campos necesarios
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
    public void deleteTask(Long id, String userMail) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Tarea no encontrada"));

        taskRepository.delete(task);
    }

    @Override
    public List<TaskResponseDTO> getTasksByUserId(Long userId, String userMail) {
        List<Task> tasks = taskRepository.findByUserId(userId);
        return tasks.stream()
                .map(taskMapper::toResponseDTO)  // Convertir cada tarea a TaskResponseDTO
                .collect(Collectors.toList());
    }

    private boolean isAdmin(Authentication authentication) {
        // Verificamos si el usuario tiene el rol de ADMIN
        return authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
    }
}
