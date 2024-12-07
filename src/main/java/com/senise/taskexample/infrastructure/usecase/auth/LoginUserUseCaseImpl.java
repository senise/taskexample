package com.senise.taskexample.infrastructure.usecase.auth;

import com.senise.taskexample.application.dto.request.LoginRequest;
import com.senise.taskexample.application.dto.response.TokenResponse;
import com.senise.taskexample.domain.entity.User;
import com.senise.taskexample.domain.usecase.auth.LoginUserUseCase;
import com.senise.taskexample.infrastructure.configuration.security.JwtService;
import com.senise.taskexample.infrastructure.respository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginUserUseCaseImpl implements LoginUserUseCase {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    public TokenResponse execute(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        return new TokenResponse(jwtService.generateToken(user));
    }
}
