package com.senise.taskexample.domain.usecase.user;

import com.senise.taskexample.application.dto.response.UserResponseDTO;
import org.springframework.security.core.Authentication;

public interface GetUserByIdUseCase {
    UserResponseDTO execute(Long id, Authentication authentication);
}
