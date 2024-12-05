package com.senise.taskexample.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO que representa la tarea en la respuesta")
public class TaskResponseDTO {

    @Schema(description = "ID único de la tarea", example = "1")
    private Long id;

    @Schema(description = "Título de la tarea", example = "Comprar leche")
    private String title;

    @Schema(description = "Descripción detallada de la tarea", example = "Ir al supermercado a comprar leche")
    private String description;

    @Schema(description = "Indica si la tarea está completada", example = "false")
    private boolean completed;

    @Schema(description = "Nombre del usuario asignado a la tarea", example = "Juan Pérez")
    private String name;
}