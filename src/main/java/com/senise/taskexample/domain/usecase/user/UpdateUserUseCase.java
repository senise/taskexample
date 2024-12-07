package com.senise.taskexample.domain.usecase.user;

import com.senise.taskexample.application.dto.request.UserRequestDTO;
import com.senise.taskexample.application.dto.response.UserResponseDTO;
import org.springframework.security.core.Authentication;

import javax.management.relation.RoleNotFoundException;

public interface UpdateUserUseCase {
    UserResponseDTO execute(Long id, UserRequestDTO userRequestDTO, Authentication authentication) throws RoleNotFoundException;
}
