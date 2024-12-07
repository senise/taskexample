package com.senise.taskexample.infrastructure.usecase.task;

import com.senise.taskexample.application.dto.response.TaskResponseDTO;
import com.senise.taskexample.application.mapper.TaskMapper;
import com.senise.taskexample.application.security.SecurityService;
import com.senise.taskexample.domain.entity.Task;
import com.senise.taskexample.domain.usecase.task.SearchTasksUseCase;
import com.senise.taskexample.infrastructure.respository.TaskRepository;
import com.senise.taskexample.infrastructure.respository.specifications.SpecificationBuilder;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SearchTasksUseCaseImpl implements SearchTasksUseCase {
    private final SecurityService securityService;
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Override
    public List<TaskResponseDTO> execute(String title, String description, Boolean completed, Authentication authentication) {
        // Obtener el correo electrónico del usuario autenticado
        String userEmail = authentication.getName(); // Se asume que el nombre del usuario es el correo electrónico

        // Comprobar si el usuario tiene rol ADMIN
        boolean isAdmin = securityService.isAdmin(authentication); // Usar el método isAdmin del SecurityService

        Specification<Task> spec = Specification.where(null);

        if (!isAdmin) {
            // Si es USER, filtrar solo las tareas asociadas al usuario con el correo electrónico
            // Usar canAccessResource para verificar si el usuario tiene acceso
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("user").get("email"), userEmail)); // Asumir que tienes un campo 'user' en Task

        }

        if (title != null && !title.isEmpty()) {
            spec = spec.and(SpecificationBuilder.containsIgnoreCase("title", title));
        }
        if (description != null && !description.isEmpty()) {
            spec = spec.and(SpecificationBuilder.containsIgnoreCase("description", description));
        }
        if (completed != null) {
            spec = spec.and(SpecificationBuilder.equals("completed", completed));
        }

        List<Task> tasks = taskRepository.findAll(spec);
        return tasks.stream()
                .map(taskMapper::toResponseDTO)
                .collect(Collectors.toList());

    }
}
