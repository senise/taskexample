package com.senise.taskexample.application.mapper;

import com.senise.taskexample.application.dto.request.TaskRequestDTO;
import com.senise.taskexample.application.dto.response.TaskResponseDTO;
import com.senise.taskexample.domain.entity.Task;
import com.senise.taskexample.domain.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class TaskMapper {

    // private final ModelMapper modelMapper;

    /**
     * Convierte una entidad Task en un DTO de respuesta.
     */
    public TaskResponseDTO toResponseDTO(Task task) {
        TaskResponseDTO dto = new TaskResponseDTO();
        dto.setCompleted(task.getCompleted());
        dto.setDescription(task.getDescription());
        dto.setName(task.getUser().getName());
        dto.setTitle(task.getTitle());
        dto.setId(task.getId());
        return dto;
        // return modelMapper.map(task, TaskResponseDTO.class);
    }

    /**
     * Convierte un DTO de solicitud en una entidad Task, asociando manualmente el User.
     */
    public Task toEntity(TaskRequestDTO dto, User user) {
        Task task = new Task();
        task.setUser(user);
        task.setDescription(dto.getDescription());
        task.setTitle(dto.getTitle());
        task.setCompleted(dto.isCompleted());
        /*Task task = modelMapper.map(dto, Task.class);
        task.setUser(user); // Asociar el usuario manualmente*/
        return task;
    }
}
