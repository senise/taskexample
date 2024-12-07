package com.senise.taskexample.domain.usecase.user;

import com.senise.taskexample.application.dto.response.UserResponseDTO;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.List;

public interface GetUsersCreatedInPeriodUseCase {
    List<UserResponseDTO> execute(LocalDateTime startDate, LocalDateTime endDate, Authentication authentication);
}
