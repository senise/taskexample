package com.senise.taskexample.infrastructure.controller;

import com.senise.taskexample.application.dto.request.TaskRequestDTO;
import com.senise.taskexample.application.dto.response.TaskResponseDTO;
import com.senise.taskexample.application.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @Mock
    private Authentication authentication;

    private TaskResponseDTO taskResponseDTO;
    private TaskRequestDTO taskRequestDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        taskResponseDTO = new TaskResponseDTO(1L, "Comprar leche", "Ir al supermercado a comprar leche", false, "Juan Pérez", LocalDateTime.now());
        taskRequestDTO = new TaskRequestDTO("Comprar leche", "Ir al supermercado a comprar leche", false, 1L);
    }

    @Test
    void createTask() {
        when(taskService.createTask(any(TaskRequestDTO.class), any(Authentication.class)))
                .thenReturn(taskResponseDTO);

        ResponseEntity<TaskResponseDTO> response = taskController.createTask(taskRequestDTO, authentication);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Comprar leche", response.getBody().getTitle());
    }

    @Test
    void getAllTasks() {
        when(taskService.getAllTasks()).thenReturn(Collections.singletonList(taskResponseDTO));

        ResponseEntity<List<TaskResponseDTO>> response = taskController.getAllTasks();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void getTaskById() {
        when(taskService.getTaskById(anyLong(), any(Authentication.class))).thenReturn(taskResponseDTO);

        ResponseEntity<TaskResponseDTO> response = taskController.getTaskById(1L, authentication);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Comprar leche", response.getBody().getTitle());
    }

    @Test
    void updateTask() {
        when(taskService.updateTask(anyLong(), any(TaskRequestDTO.class), any(Authentication.class)))
                .thenReturn(taskResponseDTO);

        ResponseEntity<TaskResponseDTO> response = taskController.updateTask(1L, taskRequestDTO, authentication);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Comprar leche", response.getBody().getTitle());
    }

    @Test
    void deleteTask() {
        doNothing().when(taskService).deleteTask(anyLong(), any(Authentication.class));

        ResponseEntity<Void> response = taskController.deleteTask(1L, authentication);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void getTasksByUserId() {
        when(taskService.getTasksByUserId(anyLong(), any(Authentication.class)))
                .thenReturn(Collections.singletonList(taskResponseDTO));

        ResponseEntity<List<TaskResponseDTO>> response = taskController.getTasksByUserId(1L, authentication);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void searchTasks() {
        when(taskService.searchTasks(anyString(), anyString(), anyBoolean(), any(Authentication.class)))
                .thenReturn(Collections.singletonList(taskResponseDTO));

        ResponseEntity<List<TaskResponseDTO>> response = taskController.searchTasks("Comprar leche", "Ir al supermercado", false, authentication);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void getTasksCreatedInPeriod() {
        when(taskService.getTasksCreatedInPeriod(any(LocalDateTime.class), any(LocalDateTime.class), any(Authentication.class)))
                .thenReturn(Collections.singletonList(taskResponseDTO));

        ResponseEntity<List<TaskResponseDTO>> response = taskController.getTasksCreatedInPeriod(LocalDateTime.now().minusDays(1), LocalDateTime.now(), authentication);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
    }
}
