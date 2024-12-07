package com.senise.taskexample.domain.usecase.user;

import org.springframework.security.core.Authentication;

public interface DeleteUserUseCase {
    void execute(Long id, Authentication authentication);
}
