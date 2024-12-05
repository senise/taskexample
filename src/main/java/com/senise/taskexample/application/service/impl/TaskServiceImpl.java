package com.senise.taskexample.application.service.impl;

import com.senise.taskexample.application.dto.request.TaskRequestDTO;
import com.senise.taskexample.application.dto.response.TaskResponseDTO;
import com.senise.taskexample.application.mapper.TaskMapper;
import com.senise.taskexample.application.service.TaskService;
import com.senise.taskexample.domain.entity.Task;
import com.senise.taskexample.domain.entity.User;
import com.senise.taskexample.domain.exception.TaskNotFoundException;
import com.senise.taskexample.domain.exception.UserNotFoundException;
import com.senise.taskexample.infrastructure.respository.TaskRepository;
import com.senise.taskexample.infrastructure.respository.UserRepository;
import lombok.AllArgsConstructor;
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
    public TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO) {
        User user = userRepository.findById(taskRequestDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));

        Task task = taskMapper.toEntity(taskRequestDTO, user);
        taskRepository.save(task);

        return taskMapper.toResponseDTO(task);
    }

    /**
     * Obtiene la lista de todas las tareas.
     */
    public List<TaskResponseDTO> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
                .map(taskMapper::toResponseDTO)
                .toList();
    }

    /**
     * Obtiene el detalle de una tarea por su ID.
     */
    public TaskResponseDTO getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Tarea no encontrada"));

        return taskMapper.toResponseDTO(task);
    }

    /**
     * Actualiza una tarea por su ID.
     */
    public TaskResponseDTO updateTask(Long id, TaskRequestDTO taskRequestDTO) {
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
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Tarea no encontrada"));

        taskRepository.delete(task);
    }

    @Override
    public List<TaskResponseDTO> getTasksByUserId(Long userId) {
        List<Task> tasks = taskRepository.findByUserId(userId);
        return tasks.stream()
                .map(taskMapper::toResponseDTO)  // Convertir cada tarea a TaskResponseDTO
                .collect(Collectors.toList());
    }
}
