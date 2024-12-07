package com.senise.taskexample.application.service.impl;

import com.senise.taskexample.application.dto.request.LoginRequest;
import com.senise.taskexample.application.dto.request.UserRequestDTO;
import com.senise.taskexample.application.dto.response.TokenResponse;
import com.senise.taskexample.application.dto.response.UserResponseDTO;
import com.senise.taskexample.application.service.AuthService;
import com.senise.taskexample.domain.usecase.auth.LoginUserUseCase;
import com.senise.taskexample.domain.usecase.auth.RegisterUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final RegisterUserUseCase registerUserUseCase;  // Uso de la interfaz
    private final LoginUserUseCase loginUserUseCase;        // Uso de la interfaz

    @Override
    public UserResponseDTO register(UserRequestDTO userRequestDTO) {
        return registerUserUseCase.execute(userRequestDTO);
    }

    @Override
    public TokenResponse login(LoginRequest loginRequest) {
        return loginUserUseCase.execute(loginRequest);
    }
}
