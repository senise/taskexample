package com.senise.taskexample.domain.usecase.user;

import com.senise.taskexample.application.dto.request.UserRequestDTO;
import com.senise.taskexample.application.dto.response.UserResponseDTO;
import org.springframework.security.core.Authentication;

public interface CreateUserUseCase {
    UserResponseDTO execute(UserRequestDTO userRequestDTO, Authentication authentication);
}
