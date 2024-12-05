package com.senise.taskexample.application.mapper;

import com.senise.taskexample.application.dto.request.UserRequestDTO;
import com.senise.taskexample.application.dto.response.UserResponseDTO;
import com.senise.taskexample.domain.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class UserMapper {

    // private final ModelMapper modelMapper;

    /**
     * Convierte un DTO de solicitud en una entidad User.
     */
    public User toEntity(UserRequestDTO userRequestDTO) {
        User user = new User();
        user.setEmail(userRequestDTO.getEmail());
        user.setName(userRequestDTO.getName());
        user.setPassword(userRequestDTO.getPassword());
        return user;
        // return modelMapper.map(userRequestDTO, User.class);
    }

    /**
     * Convierte una entidad User en un DTO de respuesta.
     */
    public UserResponseDTO toResponseDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setEmail(user.getEmail());
        dto.setName(user.getName());
        dto.setId(user.getId());
        return dto;

        // return modelMapper.map(user, UserResponseDTO.class);
    }
}
