package com.senise.taskexample.application.service;

import com.senise.taskexample.application.dto.request.LoginRequestDTO;
import com.senise.taskexample.application.dto.request.UserRequestDTO;
import com.senise.taskexample.application.dto.response.TokenResponseDTO;
import com.senise.taskexample.application.dto.response.UserResponseDTO;

public interface AuthService {

    UserResponseDTO register(UserRequestDTO userRequestDTO);

    TokenResponseDTO login(LoginRequestDTO loginRequest);
}