package com.senise.taskexample.infrastructure.respository;

import com.senise.taskexample.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    Optional<User> findByEmail(String email);

    List<User> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
}
