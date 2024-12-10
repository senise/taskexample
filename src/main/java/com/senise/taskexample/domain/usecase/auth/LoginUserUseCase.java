package com.senise.taskexample.domain.usecase.auth;

import com.senise.taskexample.application.dto.request.LoginRequestDTO;
import com.senise.taskexample.application.dto.response.TokenResponseDTO;

public interface LoginUserUseCase {
    TokenResponseDTO execute(LoginRequestDTO loginRequest);
}