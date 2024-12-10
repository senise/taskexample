package com.senise.taskexample.infrastructure.usecase.user;

import com.senise.taskexample.application.security.SecurityService;
import com.senise.taskexample.domain.entity.User;
import com.senise.taskexample.domain.usecase.user.DeleteUserUseCase;
import com.senise.taskexample.domain.usecase.user.GetUserByIdCheckUseCase;
import com.senise.taskexample.infrastructure.respository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteUserUseCaseImpl implements DeleteUserUseCase {
    private final SecurityService securityService;
    private final UserRepository userRepository;
    private final GetUserByIdCheckUseCase getUserByIdCheckUseCase;

    @Override
    public void execute(Long id, Authentication authentication) {
        User user = getUserByIdCheckUseCase.execute(id);

        // Verificar si el usuario logueado puede eliminar este perfil o si es admin
        securityService.verifyAccess(authentication, user.getEmail());

        userRepository.delete(user);
    }
}
