package com.senise.taskexample.application.service.impl;

import com.senise.taskexample.application.dto.request.UserRequestDTO;
import com.senise.taskexample.application.dto.response.UserResponseDTO;
import com.senise.taskexample.application.mapper.UserMapper;
import com.senise.taskexample.application.security.SecurityService;
import com.senise.taskexample.application.service.UserService;
import com.senise.taskexample.domain.entity.Role;
import com.senise.taskexample.domain.entity.User;
import com.senise.taskexample.domain.exception.UserNotAuthorizedException;
import com.senise.taskexample.domain.exception.UserNotFoundException;
import com.senise.taskexample.infrastructure.respository.RoleRepository;
import com.senise.taskexample.infrastructure.respository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final SecurityService securityService;

    /**
     * Crea un nuevo usuario.
     */
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO, Authentication authentication) {
        // Verificar si el usuario logueado es el mismo o es admin
        if (!securityService.canAccessResource(authentication, userRequestDTO.getEmail()) && !securityService.isAdmin(authentication)) {
            throw new UserNotAuthorizedException("No tienes permisos para crear un usuario para este correo.");
        }

        User user = userMapper.toEntity(userRequestDTO);
        userRepository.save(user);
        return userMapper.toResponseDTO(user);
    }

    /**
     * Obtiene la lista de todos los usuarios.
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")  // Solo los administradores pueden acceder
    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toResponseDTO)
                .toList();
    }

    /**
     * Obtiene el detalle de un usuario por su ID.
     */
    public UserResponseDTO getUserById(Long id, Authentication authentication) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));

        // Verificar que el usuario logueado sea el mismo o un administrador
        if (!securityService.canAccessResource(authentication, user.getEmail())) {
            throw new UserNotAuthorizedException("No tienes permisos para ver este usuario.");
        }

        return userMapper.toResponseDTO(user);
    }

    /**
     * Actualiza un usuario por su ID.
     */
    public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO, Authentication authentication) throws RoleNotFoundException {
        // Buscar al usuario existente
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));

        // Verificar si el usuario logueado puede actualizar este perfil o si es admin
        if (!securityService.canAccessResource(authentication, existingUser.getEmail()) && !securityService.isAdmin(authentication)) {
            throw new UserNotAuthorizedException("No tienes permisos para actualizar este usuario.");
        }

        // Buscar el rol a través de su nombre
        Role newRole = roleRepository.findByName(userRequestDTO.getRole())
                .orElseThrow(() -> new RoleNotFoundException("Rol no encontrado"));

        // Actualizar los campos necesarios
        existingUser.setName(userRequestDTO.getName());
        // El email no se actualiza
        // existingUser.setEmail(userRequestDTO.getEmail());

        // Actualizar el rol del usuario
        existingUser.setRole(newRole);

        // Guardar los cambios
        userRepository.save(existingUser);

        // Retornar la respuesta
        return userMapper.toResponseDTO(existingUser);
    }

    /**
     * Elimina un usuario por su ID.
     */
    public void deleteUser(Long id, Authentication authentication) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));

        // Verificar si el usuario logueado puede eliminar este perfil o si es admin
        if (!securityService.canAccessResource(authentication, user.getEmail()) && !securityService.isAdmin(authentication)) {
            throw new UserNotAuthorizedException("No tienes permisos para eliminar este usuario.");
        }

        userRepository.delete(user);
    }
}
