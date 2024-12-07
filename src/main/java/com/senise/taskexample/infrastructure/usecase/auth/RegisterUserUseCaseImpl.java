package com.senise.taskexample.infrastructure.usecase.auth;

import com.senise.taskexample.application.dto.request.UserRequestDTO;
import com.senise.taskexample.application.dto.response.UserResponseDTO;
import com.senise.taskexample.application.mapper.UserMapper;
import com.senise.taskexample.domain.entity.User;
import com.senise.taskexample.domain.usecase.auth.RegisterUserUseCase;
import com.senise.taskexample.infrastructure.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterUserUseCaseImpl implements RegisterUserUseCase {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserMapper userMapper;

    @Override
    public UserResponseDTO execute(UserRequestDTO userRequestDTO) {
        User user = userMapper.toEntity(userRequestDTO);
        user.setPassword(bCryptPasswordEncoder.encode(userRequestDTO.getPassword()));
        userRepository.save(user);
        return userMapper.toResponseDTO(user);
    }
}