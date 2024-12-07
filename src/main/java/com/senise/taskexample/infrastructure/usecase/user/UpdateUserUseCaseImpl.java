package com.senise.taskexample.infrastructure.usecase.user;

import com.senise.taskexample.application.dto.request.UserRequestDTO;
import com.senise.taskexample.application.dto.response.UserResponseDTO;
import com.senise.taskexample.application.mapper.UserMapper;
import com.senise.taskexample.application.security.SecurityService;
import com.senise.taskexample.domain.entity.Role;
import com.senise.taskexample.domain.entity.User;
import com.senise.taskexample.domain.usecase.user.GetUserByIdCheckUseCase;
import com.senise.taskexample.domain.usecase.user.UpdateUserUseCase;
import com.senise.taskexample.infrastructure.respository.RoleRepository;
import com.senise.taskexample.infrastructure.respository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;

@Service
@AllArgsConstructor
public class UpdateUserUseCaseImpl implements UpdateUserUseCase {

    private final SecurityService securityService;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final GetUserByIdCheckUseCase getUserByIdCheckUseCase;
    private final RoleRepository roleRepository;

    @Override
    public UserResponseDTO execute(Long id, UserRequestDTO userRequestDTO, Authentication authentication) throws RoleNotFoundException {
        // Buscar al usuario existente
        User existingUser = getUserByIdCheckUseCase.execute(id);

        // Verificar si el usuario logueado puede actualizar este perfil o si es admin
        securityService.verifyAccess(authentication, existingUser.getEmail());


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
}
