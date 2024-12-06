package com.senise.taskexample.application.service;

import com.senise.taskexample.application.dto.request.TaskRequestDTO;
import com.senise.taskexample.application.dto.response.TaskResponseDTO;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface TaskService {

    TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO, Authentication authentication);

    List<TaskResponseDTO> getAllTasks(/*String userMail*/);

    TaskResponseDTO getTaskById(Long id, String userMail);

    TaskResponseDTO updateTask(Long id, TaskRequestDTO taskRequestDTO, String userMail);

    void deleteTask(Long id, String userMail);

    List<TaskResponseDTO> getTasksByUserId(Long userId, String userMail);
}

