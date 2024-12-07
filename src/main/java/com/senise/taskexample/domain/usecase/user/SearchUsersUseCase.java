package com.senise.taskexample.domain.usecase.user;

import com.senise.taskexample.application.dto.response.UserResponseDTO;

import java.util.List;

public interface SearchUsersUseCase {
    List<UserResponseDTO> execute(String name, String email, String role);
}
