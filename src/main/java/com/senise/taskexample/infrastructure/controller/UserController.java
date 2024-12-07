package com.senise.taskexample.infrastructure.controller;

import com.senise.taskexample.application.dto.request.UserRequestDTO;
import com.senise.taskexample.application.dto.response.UserResponseDTO;
import com.senise.taskexample.application.service.UserService;
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
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RoleNotFoundException;
import java.time.LocalDateTime;
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
            @RequestBody @Valid UserRequestDTO userRequestDTO, Authentication authentication) {
        UserResponseDTO responseDTO = userService.createUser(userRequestDTO, authentication);
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
            @PathVariable @Parameter(description = "ID del usuario a obtener") Long id, Authentication authentication) {
        UserResponseDTO user = userService.getUserById(id, authentication);
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
            },
            parameters = {
                    @Parameter(name = "id", description = "ID del usuario que se desea actualizar.", required = true)
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(
            @PathVariable Long id,
            @RequestBody @Valid @Parameter(description = "Detalles del usuario a actualizar") UserRequestDTO userRequestDTO, Authentication authentication) throws RoleNotFoundException {
        UserResponseDTO updatedUser = userService.updateUser(id, userRequestDTO, authentication);
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
            @PathVariable @Parameter(description = "ID del usuario a eliminar") Long id, Authentication authentication) {
        userService.deleteUser(id, authentication);
        return ResponseEntity.noContent().build();
    }

    /**
     * Buscar usuarios por múltiples criterios
     */
    @Operation(
            summary = "Buscar usuarios por múltiples criterios",
            description = "Permite buscar usuarios por nombre, email o rol. Todos los parámetros son opcionales, y se puede combinar cualquiera de ellos."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Búsqueda realizada con éxito", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserResponseDTO.class)))
            }),
            @ApiResponse(responseCode = "204", description = "No se encontraron usuarios que coincidan con los criterios de búsqueda", content = @Content),
            @ApiResponse(responseCode = "400", description = "Parámetros de búsqueda inválidos", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping("/search")
    public ResponseEntity<List<UserResponseDTO>> searchUsers(
            @RequestParam(required = false) @Parameter(description = "Nombre del usuario") String name,
            @RequestParam(required = false) @Parameter(description = "Email del usuario") String email,
            @RequestParam(required = false) @Parameter(description = "Rol del usuario") String role) {
        List<UserResponseDTO> users = userService.searchUsers(name, email, role);

        if (users.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content si no hay resultados
        }

        return ResponseEntity.ok(users); // 200 OK con la lista de usuarios
    }

    /**
     * Buscar usuarios creados en un periodo de tiempo
     */
    @Operation(
            summary = "Buscar usuarios creados en un periodo de tiempo",
            description = "Permite buscar usuarios creados dentro de un rango de fechas. Los parámetros de fecha son obligatorios."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Búsqueda realizada con éxito", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserResponseDTO.class)))
            }),
            @ApiResponse(responseCode = "400", description = "Parámetros de fecha inválidos", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping("/created-in-period")
    public ResponseEntity<List<UserResponseDTO>> getUsersCreatedInPeriod(
            @RequestParam @Parameter(description = "Fecha de inicio del periodo") LocalDateTime startDate,
            @RequestParam @Parameter(description = "Fecha de fin del periodo") LocalDateTime endDate,
            Authentication authentication) {
        List<UserResponseDTO> users = userService.getUsersCreatedInPeriod(startDate, endDate, authentication );

        if (users.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content si no hay resultados
        }

        return ResponseEntity.ok(users); // 200 OK con la lista de usuarios
    }


}


