package com.senise.taskexample.infrastructure.controller;

import com.senise.taskexample.application.dto.request.TaskRequestDTO;
import com.senise.taskexample.application.dto.response.TaskResponseDTO;
import com.senise.taskexample.application.service.TaskService;
import com.senise.taskexample.infrastructure.configuration.security.UserDetailServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@AllArgsConstructor
@Tag(name = "Tasks", description = "Operaciones relacionadas con las tareas")
public class TaskController {

    private final TaskService taskService;

    /**
     * Crea una nueva tarea.
     */
    @Operation(
            summary = "Crear una nueva tarea",
            description = "Este endpoint permite crear una nueva tarea en el sistema.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Tarea creada con éxito"),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos proporcionados")
            }
    )
    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(
            @RequestBody @Valid @Parameter(description = "Detalles de la tarea que se va a crear") TaskRequestDTO taskRequestDTO,
            Authentication authentication) {
        TaskResponseDTO responseDTO = taskService.createTask(taskRequestDTO, authentication);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    /**
     * Obtiene todas las tareas.
     */
    @Operation(
            summary = "Obtener todas las tareas",
            description = "Este endpoint devuelve una lista con todas las tareas almacenadas en el sistema.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de tareas obtenida con éxito")
            }
    )
    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> getAllTasks() {
        List<TaskResponseDTO> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    /**
     * Obtiene el detalle de una tarea por su ID.
     */
    @Operation(
            summary = "Obtener detalles de una tarea",
            description = "Este endpoint devuelve los detalles de una tarea específica por su ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tarea encontrada"),
                    @ApiResponse(responseCode = "404", description = "Tarea no encontrada")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getTaskById(
            @PathVariable @Parameter(description = "ID de la tarea a obtener", example = "1") Long id,
            Authentication authentication) {
        TaskResponseDTO task = taskService.getTaskById(id, authentication);
        return ResponseEntity.ok(task);
    }

    /**
     * Actualiza una tarea por su ID.
     */
    @Operation(
            summary = "Actualizar tarea existente",
            description = "Este endpoint permite actualizar una tarea existente por su ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tarea actualizada con éxito"),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos proporcionados"),
                    @ApiResponse(responseCode = "404", description = "Tarea no encontrada")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> updateTask(
            @PathVariable @Parameter(description = "ID de tarea que se desea actualizar.", example = "1") Long id,
            @RequestBody @Valid @Parameter(description = "Detalles de la tarea a actualizar") TaskRequestDTO taskRequestDTO,
            Authentication authentication) {
        TaskResponseDTO updatedTask = taskService.updateTask(id, taskRequestDTO, authentication);
        return ResponseEntity.ok(updatedTask);
    }

    /**
     * Elimina una tarea por su ID.
     */
    @Operation(
            summary = "Eliminar una tarea",
            description = "Este endpoint permite eliminar una tarea por su ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Tarea eliminada con éxito"),
                    @ApiResponse(responseCode = "404", description = "Tarea no encontrada")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable @Parameter(description = "ID de la tarea a eliminar", example = "1") Long id,
            Authentication authentication) {
        taskService.deleteTask(id, authentication);
        return ResponseEntity.noContent().build();
    }

    /**
     * Obtiene todas las tareas de un usuario específico.
     *
     * @param userId ID del usuario cuyas tareas se desean obtener.
     * @return Lista de tareas asociadas al usuario.
     */
    @Operation(
            summary = "Obtener todas las tareas de un usuario",
            description = "Este endpoint devuelve todas las tareas asociadas al usuario con el ID especificado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de tareas obtenida con éxito", content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = TaskResponseDTO.class)))
                    }),
                    @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
            }
    )
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TaskResponseDTO>> getTasksByUserId(
            @PathVariable @Parameter(description = "ID del usuario cuyas tareas se desean obtener", example = "1") Long userId,
            Authentication authentication) {
        List<TaskResponseDTO> tasks = taskService.getTasksByUserId(userId, authentication);
        return ResponseEntity.ok(tasks);
    }

    /**
     * Buscar tareas por múltiples criterios.
     */
    @Operation(
            summary = "Buscar tareas por múltiples criterios",
            description = "Permite buscar tareas filtrando por título, descripción o estado de completado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Búsqueda realizada con éxito", content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = TaskResponseDTO.class)))
                    }),
                    @ApiResponse(responseCode = "400", description = "Parámetros de búsqueda inválidos", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
            }
    )
    @GetMapping("/search")
    public ResponseEntity<List<TaskResponseDTO>> searchTasks(
            @RequestParam(required = false) @Parameter(description = "Título de la tarea", example = "Mi tarea") String title,
            @RequestParam(required = false) @Parameter(description = "Descripción de la tarea", example = "Esta es una descripción") String description,
            @RequestParam(required = false) @Parameter(description = "Estado de completado de la tarea (true/false)", example = "true") Boolean completed,
            Authentication authentication) {
        List<TaskResponseDTO> tasks = taskService.searchTasks(title, description, completed, authentication);

        if (tasks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(tasks);
    }

    /**
     * Buscar tareas creadas en un periodo de tiempo.
     */
    @Operation(
            summary = "Buscar tareas creadas en un periodo de tiempo",
            description = "Permite buscar tareas creadas dentro de un rango de fechas.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Búsqueda realizada con éxito", content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = TaskResponseDTO.class)))
                    }),
                    @ApiResponse(responseCode = "400", description = "Parámetros de fecha inválidos", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
            }
    )
    @GetMapping("/created-in-period")
    public ResponseEntity<List<TaskResponseDTO>> getTasksCreatedInPeriod(
            @RequestParam @Parameter(description = "Fecha de inicio del periodo", example = "2024-01-01T00:00:00") LocalDateTime startDate,
            @RequestParam @Parameter(description = "Fecha de fin del periodo", example = "2024-01-31T23:59:59") LocalDateTime endDate,
            Authentication authentication) {
        List<TaskResponseDTO> tasks = taskService.getTasksCreatedInPeriod(startDate, endDate, authentication);

        if (tasks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(tasks);
    }
}
