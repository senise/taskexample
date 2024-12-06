package com.senise.taskexample.infrastructure.controller;

import com.senise.taskexample.application.dto.request.UserRequestDTO;
import com.senise.taskexample.application.dto.response.UserResponseDTO;
import com.senise.taskexample.application.service.UserService;
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
@RequestMapping("/api/v1/users")
@AllArgsConstructor
@Tag(name = "Users", description = "Operaciones relacionadas con los usuarios")
public class UserController {

    private final UserService userService;

    /**
     * Crea un nuevo usuario.
     */
    @Operation(
            summary = "Crear un nuevo usuario",
            description = "Este endpoint permite crear un nuevo usuario en el sistema.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Usuario creado con éxito"),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos proporcionados")
            }
    )
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(
            @RequestBody @Valid @Parameter(description = "Detalles del usuario que se va a crear") UserRequestDTO userRequestDTO) {
        UserResponseDTO responseDTO = userService.createUser(userRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    /**
     * Obtiene la lista de todos los usuarios.
     */
    @Operation(
            summary = "Obtener todos los usuarios",
            description = "Este endpoint devuelve una lista con todos los usuarios almacenados en el sistema.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida con éxito")
            }
    )
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * Obtiene el detalle de un usuario por su ID.
     */
    @Operation(
            summary = "Obtener detalles de un usuario",
            description = "Este endpoint devuelve los detalles de un usuario específico por su ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
                    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(
            @PathVariable @Parameter(description = "ID del usuario a obtener") Long id) {
        UserResponseDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    /**
     * Actualiza un usuario por su ID.
     */
    @Operation(
            summary = "Actualizar usuario existente",
            description = "Este endpoint permite actualizar un usuario existente por su ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuario actualizado con éxito"),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos proporcionados"),
                    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(
            @PathVariable Long id,
            @RequestBody @Valid @Parameter(description = "Detalles del usuario a actualizar") UserRequestDTO userRequestDTO) {
        UserResponseDTO updatedUser = userService.updateUser(id, userRequestDTO);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Elimina un usuario por su ID.
     */
    @Operation(
            summary = "Eliminar un usuario",
            description = "Este endpoint permite eliminar un usuario por su ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Usuario eliminado con éxito"),
                    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable @Parameter(description = "ID del usuario a eliminar") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}


