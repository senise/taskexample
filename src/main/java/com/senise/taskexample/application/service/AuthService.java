package com.senise.taskexample.application.service;

import com.senise.taskexample.application.dto.request.LoginRequest;
import com.senise.taskexample.application.dto.request.UserRequestDTO;

public interface AuthService {

    void register(UserRequestDTO userRequestDTO);

    String login(LoginRequest loginRequest);
}