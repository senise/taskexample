package com.senise.taskexample.application.service;

import com.senise.taskexample.application.dto.request.TaskRequestDTO;
import com.senise.taskexample.application.dto.response.TaskResponseDTO;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskService {

    TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO, Authentication authentication);

    List<TaskResponseDTO> getAllTasks();

    TaskResponseDTO getTaskById(Long id, Authentication authentication);

    TaskResponseDTO updateTask(Long id, TaskRequestDTO taskRequestDTO, Authentication authentication);

    void deleteTask(Long id, Authentication authentication);

    List<TaskResponseDTO> getTasksByUserId(Long userId, Authentication authentication);

    List<TaskResponseDTO> searchTasks(String title, String description, Boolean completed, Authentication authentication);

    List<TaskResponseDTO> getTasksCreatedInPeriod(LocalDateTime startDate, LocalDateTime endDate, Authentication authentication);
}

