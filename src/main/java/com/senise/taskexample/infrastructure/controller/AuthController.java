package com.senise.taskexample.infrastructure.controller;

import com.senise.taskexample.application.dto.request.LoginRequestDTO;
import com.senise.taskexample.application.dto.request.UserRequestDTO;
import com.senise.taskexample.application.dto.response.TokenResponseDTO;
import com.senise.taskexample.application.dto.response.UserResponseDTO;
import com.senise.taskexample.application.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth", description = "Operaciones relacionadas con el login de usuario")
@RestController
@RequestMapping(path = "api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Endpoint para registrar un nuevo usuario.
     * Permite a un usuario crear una cuenta proporcionando sus datos.
     */
    @Operation(
            summary = "Registrar un nuevo usuario",
            description = "Este endpoint permite a un usuario registrarse proporcionando sus datos."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Registro exitoso"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta - Detalles del usuario inválidos")
    })
    @PostMapping(path = "/register")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        return ResponseEntity.ok(authService.register(userRequestDTO));
    }

    /**
     * Endpoint para iniciar sesión con un usuario registrado.
     * Devuelve un token JWT para acceder a otros recursos protegidos.
     */
    @Operation(
            summary = "Iniciar sesión de un usuario",
            description = "Este endpoint permite a un usuario iniciar sesión y obtener un token JWT para acceder a recursos protegidos."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inicio de sesión exitoso", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "No autorizado - Credenciales inválidas")
    })
    @PostMapping(path = "/login")
    public ResponseEntity<TokenResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }
}
