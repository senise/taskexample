package com.senise.taskexample.infrastructure.usecase.user;

import com.senise.taskexample.application.dto.response.UserResponseDTO;
import com.senise.taskexample.application.mapper.UserMapper;
import com.senise.taskexample.domain.entity.User;
import com.senise.taskexample.domain.usecase.user.SearchUsersUseCase;
import com.senise.taskexample.infrastructure.respository.UserRepository;
import com.senise.taskexample.infrastructure.respository.specifications.SpecificationBuilder;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SearchUsersUseCaseImpl implements SearchUsersUseCase {
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    public List<UserResponseDTO> execute(String name, String email, String role) {
        Specification<User> spec = Specification.where(null);

        if (name != null && !name.isEmpty()) {
            spec = spec.and(SpecificationBuilder.containsIgnoreCase("name", name));
        }

        if (email != null && !email.isEmpty()) {
            spec = spec.and(SpecificationBuilder.containsIgnoreCase("email", email));
        }

        if (role != null && !role.isEmpty()) {
            spec = spec.and(SpecificationBuilder.containsIgnoreCase("role.name", role)); // Acceso a relaciones
        }

        List<User> users = userRepository.findAll(spec);
        return users.stream()
                .map(userMapper::toResponseDTO)
                .toList();
    }
}
