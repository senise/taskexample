package com.senise.taskexample.infrastructure.controller;

import com.senise.taskexample.application.dto.request.TaskRequestDTO;
import com.senise.taskexample.application.dto.response.TaskResponseDTO;
import com.senise.taskexample.application.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
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
            @RequestBody @Valid @Parameter(description = "Detalles de la tarea que se va a crear") TaskRequestDTO taskRequestDTO) {
        TaskResponseDTO responseDTO = taskService.createTask(taskRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    /**
     * Obtiene la lista de todas las tareas.
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
            @PathVariable @Parameter(description = "ID de la tarea a obtener") Long id) {
        TaskResponseDTO task = taskService.getTaskById(id);
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
            @PathVariable Long id,
            @RequestBody @Valid @Parameter(description = "Detalles de la tarea a actualizar") TaskRequestDTO taskRequestDTO) {
        TaskResponseDTO updatedTask = taskService.updateTask(id, taskRequestDTO);
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
            @PathVariable @Parameter(description = "ID de la tarea a eliminar") Long id) {
        taskService.deleteTask(id);
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
            parameters = {
                    @Parameter(name = "userId", description = "ID del usuario cuyas tareas se desean obtener", required = true)
            }
    )
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TaskResponseDTO>> getTasksByUserId(@PathVariable Long userId) {
        List<TaskResponseDTO> tasks = taskService.getTasksByUserId(userId);
        return ResponseEntity.ok(tasks);
    }
}
