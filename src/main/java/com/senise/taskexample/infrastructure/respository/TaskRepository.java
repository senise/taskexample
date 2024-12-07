package com.senise.taskexample.infrastructure.respository;

import com.senise.taskexample.domain.entity.Task;
import com.senise.taskexample.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {
    List<Task> findByUserId(Long userId);

    List<Task> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<Task> findByUserAndCreatedAtBetween(User currentUser, LocalDateTime startDate, LocalDateTime endDate);
}

