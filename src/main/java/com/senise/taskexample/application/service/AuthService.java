package com.senise.taskexample.application.service;

import com.senise.taskexample.application.dto.request.LoginRequest;
import com.senise.taskexample.application.dto.request.UserRequestDTO;
import com.senise.taskexample.application.dto.response.TokenResponse;
import com.senise.taskexample.application.dto.response.UserResponseDTO;

public interface AuthService {

    UserResponseDTO register(UserRequestDTO userRequestDTO);

    TokenResponse login(LoginRequest loginRequest);
}