package com.senise.taskexample.application.mapper;

import com.senise.taskexample.application.dto.request.UserRequestDTO;
import com.senise.taskexample.application.dto.response.UserResponseDTO;
import com.senise.taskexample.domain.entity.User;
import com.senise.taskexample.infrastructure.respository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class UserMapper {

    private final RoleRepository roleRepository;

    public User toEntity(UserRequestDTO userRequestDTO) {
        User user = new User();
        user.setEmail(userRequestDTO.getEmail());
        user.setName(userRequestDTO.getName());
        user.setPassword(userRequestDTO.getPassword());

        // Obtener el rol desde la base de datos y asignarlo al usuario
       /* Role role = roleRepository.findByName(userRequestDTO.getRole())
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado: " + userRequestDTO.getRole()));

        user.setRole(role); // Asignar un único rol al usuario*/
        return user;
    }

    public UserResponseDTO toResponseDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setEmail(user.getEmail());
        dto.setName(user.getName());
        dto.setId(user.getId());
        return dto;
    }
}
