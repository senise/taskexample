package com.senise.taskexample.infrastructure.usecase.user;

import com.senise.taskexample.application.dto.request.UserRequestDTO;
import com.senise.taskexample.application.dto.response.UserResponseDTO;
import com.senise.taskexample.application.mapper.UserMapper;
import com.senise.taskexample.application.security.SecurityService;
import com.senise.taskexample.domain.entity.User;
import com.senise.taskexample.domain.usecase.user.CreateUserUseCase;
import com.senise.taskexample.infrastructure.respository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateUserUseCaseImpl implements CreateUserUseCase {
    private final SecurityService securityService;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    public UserResponseDTO execute(UserRequestDTO userRequestDTO, Authentication authentication) {
        // Verificar si el usuario logueado es el mismo o es admin
        securityService.verifyAccess(authentication, userRequestDTO.getEmail());

        User user = userMapper.toEntity(userRequestDTO);
        userRepository.save(user);
        return userMapper.toResponseDTO(user);
    }
}
