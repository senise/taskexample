package com.senise.taskexample.application.service.impl;

import com.senise.taskexample.application.dto.request.LoginRequestDTO;
import com.senise.taskexample.application.dto.request.UserRequestDTO;
import com.senise.taskexample.application.dto.response.TokenResponseDTO;
import com.senise.taskexample.application.dto.response.UserResponseDTO;
import com.senise.taskexample.application.service.AuthService;
import com.senise.taskexample.domain.usecase.auth.LoginUserUseCase;
import com.senise.taskexample.domain.usecase.auth.RegisterUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final RegisterUserUseCase registerUserUseCase;
    private final LoginUserUseCase loginUserUseCase;

    /**
     * Registrar usuario.
     */
    @Override
    public UserResponseDTO register(UserRequestDTO userRequestDTO) {
        return registerUserUseCase.execute(userRequestDTO);
    }

    /**
     * Login de usuario
     */
    @Override
    public TokenResponseDTO login(LoginRequestDTO loginRequest) {
        return loginUserUseCase.execute(loginRequest);
    }
}
