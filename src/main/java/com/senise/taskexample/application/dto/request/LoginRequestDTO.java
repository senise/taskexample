package com.senise.taskexample.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Schema(description = "Detalles del usuario que se va a logear")
public class LoginRequestDTO {

    @Schema(description = "Correo electrónico del usuario", example = "juan.perez@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String email;

    @Schema(description = "Contraseña del usuario", example = "Abc1234.", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String password;
}
