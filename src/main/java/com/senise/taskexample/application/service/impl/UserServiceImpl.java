package com.senise.taskexample.application.service.impl;

import com.senise.taskexample.application.dto.request.UserRequestDTO;
import com.senise.taskexample.application.dto.response.UserResponseDTO;
import com.senise.taskexample.application.service.UserService;
import com.senise.taskexample.domain.usecase.user.*;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final CreateUserUseCase createUserUseCase;
    private final GetAllUsersUseCase getAllUsersUseCase;
    private final GetUserByIdUseCase getUserByIdUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final SearchUsersUseCase searchUsersUseCase;
    private final GetUsersCreatedInPeriodUseCase getUsersCreatedInPeriodUseCase;

    /**
     * Crea un nuevo usuario.
     */
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO, Authentication authentication) {
        return createUserUseCase.execute(userRequestDTO, authentication);
    }

    /**
     * Obtiene la lista de todos los usuarios.
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")  // Solo los administradores pueden acceder
    public List<UserResponseDTO> getAllUsers() {
        return getAllUsersUseCase.execute();
    }

    /**
     * Obtiene el detalle de un usuario por su ID.
     */
    public UserResponseDTO getUserById(Long id, Authentication authentication) {
        return getUserByIdUseCase.execute(id, authentication);
    }

    /**
     * Actualiza un usuario por su ID.
     */
    public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO, Authentication authentication) throws RoleNotFoundException {
        return updateUserUseCase.execute(id, userRequestDTO, authentication);
    }

    /**
     * Elimina un usuario por su ID.
     */
    public void deleteUser(Long id, Authentication authentication) {
        deleteUserUseCase.execute(id, authentication);
    }

    /**
     * Busca usuarios por criterios.
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")  // Solo para administradores
    @Override
    public List<UserResponseDTO> searchUsers(String name, String email, String role) {
        return searchUsersUseCase.execute(name, email, role);
    }

    /**
     * Busca usuarios creados dentro de un período de tiempo.
     */
    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")  // Solo para administradores
    public List<UserResponseDTO> getUsersCreatedInPeriod(LocalDateTime startDate, LocalDateTime endDate, Authentication authentication) {
        return getUsersCreatedInPeriodUseCase.execute(startDate, endDate, authentication);
    }
}
