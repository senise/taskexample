package com.senise.taskexample.infrastructure.usecase.user;

import com.senise.taskexample.application.dto.response.UserResponseDTO;
import com.senise.taskexample.application.mapper.UserMapper;
import com.senise.taskexample.application.security.SecurityService;
import com.senise.taskexample.domain.entity.User;
import com.senise.taskexample.domain.exception.UserNotAuthorizedException;
import com.senise.taskexample.domain.usecase.user.GetUserByIdCheckUseCase;
import com.senise.taskexample.domain.usecase.user.GetUserByIdUseCase;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetUserByIdUseCaseImpl implements GetUserByIdUseCase {
    private final SecurityService securityService;
    private final UserMapper userMapper;
    private final GetUserByIdCheckUseCase getUserByIdCheckUseCase;

    @Override
    public UserResponseDTO execute(Long id, Authentication authentication) {
        User user = getUserByIdCheckUseCase.execute(id);

        // Verificar que el usuario logueado sea el mismo o un administrador
        if (securityService.canAccessResource(authentication, user.getEmail())) {
            throw new UserNotAuthorizedException("No tienes permisos para ver este usuario.");
        }

        return userMapper.toResponseDTO(user);
    }
}
