package com.senise.taskexample.application.service;

import com.senise.taskexample.application.dto.request.UserRequestDTO;
import com.senise.taskexample.application.dto.response.UserResponseDTO;
import org.springframework.security.core.Authentication;

import javax.management.relation.RoleNotFoundException;
import java.util.List;


public interface UserService {

    UserResponseDTO createUser(UserRequestDTO userRequestDTO, Authentication authentication);

    List<UserResponseDTO> getAllUsers();

    UserResponseDTO getUserById(Long id, Authentication authentication);

    UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO, Authentication authentication) throws RoleNotFoundException;

    void deleteUser(Long id, Authentication authentication);
}


