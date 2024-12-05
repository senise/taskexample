package com.senise.taskexample.application.service.impl;

import com.senise.taskexample.application.dto.request.LoginRequest;
import com.senise.taskexample.application.dto.request.UserRequestDTO;
import com.senise.taskexample.application.service.AuthService;
import com.senise.taskexample.domain.entity.User;
import com.senise.taskexample.infrastructure.configuration.security.JwtService;
import com.senise.taskexample.infrastructure.respository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void register(UserRequestDTO userRequestDTO) {
        User u = User.builder()
                .email(userRequestDTO.getEmail())
                .password(bCryptPasswordEncoder.encode(userRequestDTO.getPassword()))
                .name(userRequestDTO.getName())
                //.firstName(userRequestDTO.getFirstName())
                //.lastName(userRequestDTO.getLastName())
                .build();
        userRepository.save(u);
    }

    public String login(LoginRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        User u = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new EntityNotFoundException("User not found"));

        return jwtService.generateToken(u);

    }
}
