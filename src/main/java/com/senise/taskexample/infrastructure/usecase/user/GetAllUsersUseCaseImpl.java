package com.senise.taskexample.infrastructure.usecase.user;

import com.senise.taskexample.application.dto.response.UserResponseDTO;
import com.senise.taskexample.application.mapper.UserMapper;
import com.senise.taskexample.domain.entity.User;
import com.senise.taskexample.domain.usecase.user.GetAllUsersUseCase;
import com.senise.taskexample.infrastructure.respository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetAllUsersUseCaseImpl implements GetAllUsersUseCase {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    public List<UserResponseDTO> execute() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toResponseDTO)
                .toList();
    }
}
