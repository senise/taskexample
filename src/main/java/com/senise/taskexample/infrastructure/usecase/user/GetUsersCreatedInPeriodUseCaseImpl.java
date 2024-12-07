package com.senise.taskexample.infrastructure.usecase.user;

import com.senise.taskexample.application.dto.response.UserResponseDTO;
import com.senise.taskexample.application.mapper.UserMapper;
import com.senise.taskexample.application.security.SecurityService;
import com.senise.taskexample.domain.entity.User;
import com.senise.taskexample.domain.exception.UserNotAuthorizedException;
import com.senise.taskexample.domain.usecase.user.GetUsersCreatedInPeriodUseCase;
import com.senise.taskexample.infrastructure.respository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GetUsersCreatedInPeriodUseCaseImpl implements GetUsersCreatedInPeriodUseCase {
    private final SecurityService securityService;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    public List<UserResponseDTO> execute(LocalDateTime startDate, LocalDateTime endDate, Authentication authentication) {
        // Verificamos que el usuario autenticado tenga el rol de administrador
        if (!securityService.isAdmin(authentication)) {
            throw new UserNotAuthorizedException("Acceso denegado. Solo administradores pueden realizar esta operación.");
        }

        // Obtenemos todos los usuarios creados en el rango de fechas especificado
        List<User> users = userRepository.findByCreatedAtBetween(startDate, endDate);

        // Convertimos los usuarios a DTOs y los devolvemos
        return users.stream()
                .map(userMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
