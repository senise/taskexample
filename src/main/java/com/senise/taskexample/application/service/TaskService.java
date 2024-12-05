package com.senise.taskexample.application.service;

import com.senise.taskexample.application.dto.request.TaskRequestDTO;
import com.senise.taskexample.application.dto.response.TaskResponseDTO;

import java.util.List;

public interface TaskService {

    TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO);

    List<TaskResponseDTO> getAllTasks();

    TaskResponseDTO getTaskById(Long id);

    TaskResponseDTO updateTask(Long id, TaskRequestDTO taskRequestDTO);

    void deleteTask(Long id);

    List<TaskResponseDTO> getTasksByUserId(Long userId);
}

