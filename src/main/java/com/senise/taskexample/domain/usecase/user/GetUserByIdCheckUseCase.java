package com.senise.taskexample.domain.usecase.user;

import com.senise.taskexample.domain.entity.User;

public interface GetUserByIdCheckUseCase {
    User execute(Long userId);
}
