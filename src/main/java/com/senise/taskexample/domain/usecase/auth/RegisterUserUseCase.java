package com.senise.taskexample.domain.usecase.auth;

import com.senise.taskexample.application.dto.request.UserRequestDTO;
import com.senise.taskexample.application.dto.response.UserResponseDTO;

public interface RegisterUserUseCase {
    UserResponseDTO execute(UserRequestDTO userRequestDTO);
}
