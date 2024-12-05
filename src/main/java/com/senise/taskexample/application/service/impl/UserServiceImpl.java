package com.senise.taskexample.application.service.impl;

import com.senise.taskexample.application.dto.request.UserRequestDTO;
import com.senise.taskexample.application.dto.response.UserResponseDTO;
import com.senise.taskexample.application.mapper.UserMapper;
import com.senise.taskexample.application.service.UserService;
import com.senise.taskexample.domain.entity.User;
import com.senise.taskexample.domain.exception.UserNotFoundException;
import com.senise.taskexample.infrastructure.respository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * Crea un nuevo usuario.
     */
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        User user = userMapper.toEntity(userRequestDTO);
        userRepository.save(user);
        return userMapper.toResponseDTO(user);
    }

    /**
     * Obtiene la lista de todos los usuarios.
     */
    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toResponseDTO)
                .toList();
    }

    /**
     * Obtiene el detalle de un usuario por su ID.
     */
    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));
        return userMapper.toResponseDTO(user);
    }

    /**
     * Actualiza un usuario por su ID.
     */
    public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));

        // Actualizar los campos necesarios
        existingUser.setName(userRequestDTO.getName());
        existingUser.setEmail(userRequestDTO.getEmail());

        userRepository.save(existingUser);
        return userMapper.toResponseDTO(existingUser);
    }

    /**
     * Elimina un usuario por su ID.
     */
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));
        userRepository.delete(user);
    }
}
