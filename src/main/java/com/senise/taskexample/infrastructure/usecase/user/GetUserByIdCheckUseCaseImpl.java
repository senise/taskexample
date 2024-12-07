package com.senise.taskexample.infrastructure.usecase.user;

import com.senise.taskexample.domain.entity.User;
import com.senise.taskexample.domain.exception.UserNotFoundException;
import com.senise.taskexample.domain.usecase.user.GetUserByIdCheckUseCase;
import com.senise.taskexample.infrastructure.respository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetUserByIdCheckUseCaseImpl implements GetUserByIdCheckUseCase {

    private UserRepository userRepository;

    @Override
    public User execute(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));
    }
}
