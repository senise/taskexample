package com.senise.taskexample.application.service;

import com.senise.taskexample.application.dto.request.UserRequestDTO;
import com.senise.taskexample.application.dto.response.UserResponseDTO;

import java.util.List;


public interface UserService {

    UserResponseDTO createUser(UserRequestDTO userRequestDTO);

    List<UserResponseDTO> getAllUsers();

    UserResponseDTO getUserById(Long id);

    UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO);

    void deleteUser(Long id);
}


