package com.senise.taskexample.domain.usecase.auth;

import com.senise.taskexample.application.dto.request.LoginRequest;
import com.senise.taskexample.application.dto.response.TokenResponse;

public interface LoginUserUseCase {
    TokenResponse execute(LoginRequest loginRequest);
}