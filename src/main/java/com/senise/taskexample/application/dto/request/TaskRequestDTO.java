package com.senise.taskexample.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para crear o actualizar una tarea")
public class TaskRequestDTO {

    @Schema(description = "Título de la tarea", example = "Comprar leche", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String title;

    @Schema(description = "Descripción detallada de la tarea", example = "Ir al supermercado a comprar leche", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String description;

    @Schema(description = "Indica si la tarea está completada", example = "false")
    private boolean completed;

    @Schema(description = "ID del usuario asociado a la tarea", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Long userId;
}

