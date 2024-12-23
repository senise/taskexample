package com.senise.taskexample.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para crear o actualizar un usuario")
public class UserRequestDTO {

    @Schema(description = "Nombre del usuario", example = "Juan Pérez", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "El nombre de usuario es obligatorio")
    private String name;

    @Schema(description = "Correo electrónico del usuario", example = "juan.perez@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El formato del campo debe ser el de un email")
    private String email;

    @Schema(description = "Contraseña del usuario", example = "Abc1234.", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "La contraseña es obligatoria")
    private String password;

    @Schema(description = "Rol del usuario", example = "ROLE_USER", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "El rol de usuario es obligatorio")
    private String role;
}
