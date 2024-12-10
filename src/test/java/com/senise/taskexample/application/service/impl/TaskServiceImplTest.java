package com.senise.taskexample.application.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senise.taskexample.application.dto.request.TaskRequestDTO;
import com.senise.taskexample.application.dto.response.TaskResponseDTO;
import com.senise.taskexample.domain.usecase.task.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskServiceImplTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private CreateTaskUseCase createTaskUseCase;

    @Mock
    private GetAllTasksUseCase getAllTasksUseCase;

    @Mock
    private GetTaskByIdUseCase getTaskByIdUseCase;

    @Mock
    private UpdateTaskUseCase updateTaskUseCase;

    @Mock
    private DeleteTaskUseCase deleteTaskUseCase;

    @Mock
    private GetTasksByUserIdUseCase getTasksByUserIdUseCase;

    @Mock
    private SearchTasksUseCase searchTasksUseCase;

    @Mock
    private GetTasksCreatedInPeriodUseCase getTasksCreatedInPeriodUseCase;

    @InjectMocks
    private TaskServiceImpl taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test for createTask method
    @Test
    void testCreateTask() throws Exception {
        // Datos de entrada
        TaskRequestDTO taskRequestDTO = new TaskRequestDTO("Task 1", "Description of Task 1", false, 1L);

        // Datos de respuesta esperados
        TaskResponseDTO taskResponseDTO = new TaskResponseDTO(1L, "Task 1", "Description of Task 1", false, "Juan Pérez", LocalDateTime.now());

        // Simular el comportamiento del use case
        when(createTaskUseCase.execute(any(TaskRequestDTO.class), any(Authentication.class))).thenReturn(taskResponseDTO);

        // Realizar la solicitud POST y verificar la respuesta
        mockMvc.perform(post("/api/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Task 1"))
                .andExpect(jsonPath("$.description").value("Description of Task 1"))
                .andExpect(jsonPath("$.completed").value(false))
                .andExpect(jsonPath("$.name").value("Juan Pérez"));
    }

    // Test for getAllTasks method
    @Test
    void testGetAllTasks() throws Exception {
        // Datos de respuesta esperados
        TaskResponseDTO taskResponseDTO1 = new TaskResponseDTO(1L, "Task 1", "Description of Task 1", false, "Juan Pérez", LocalDateTime.now());
        TaskResponseDTO taskResponseDTO2 = new TaskResponseDTO(2L, "Task 2", "Description of Task 2", true, "Juan Pérez", LocalDateTime.now());

        // Simular el comportamiento del use case
        when(getAllTasksUseCase.execute()).thenReturn(List.of(taskResponseDTO1, taskResponseDTO2));

        // Realizar la solicitud GET y verificar la respuesta
        mockMvc.perform(get("/api/v1/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L));
    }

    // Test for getTaskById method
    @Test
    void testGetTaskById() throws Exception {
        // Datos de respuesta esperados
        TaskResponseDTO taskResponseDTO = new TaskResponseDTO(1L, "Task 1", "Description of Task 1", false, "Juan Pérez", LocalDateTime.now());

        // Simular el comportamiento del use case
        when(getTaskByIdUseCase.execute(eq(1L), any(Authentication.class))).thenReturn(taskResponseDTO);

        // Realizar la solicitud GET y verificar la respuesta
        mockMvc.perform(get("/api/v1/tasks/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Task 1"))
                .andExpect(jsonPath("$.description").value("Description of Task 1"))
                .andExpect(jsonPath("$.name").value("Juan Pérez"));
    }

    // Test for updateTask method
    @Test
    void testUpdateTask() throws Exception {
        // Datos de entrada
        TaskRequestDTO taskRequestDTO = new TaskRequestDTO("Updated Task", "Updated description", true, 1L);

        // Datos de respuesta esperados
        TaskResponseDTO taskResponseDTO = new TaskResponseDTO(1L, "Updated Task", "Updated description", true, "Juan Pérez", LocalDateTime.now());

        // Simular el comportamiento del use case
        when(updateTaskUseCase.execute(eq(1L), any(TaskRequestDTO.class), any(Authentication.class))).thenReturn(taskResponseDTO);

        // Realizar la solicitud PUT y verificar la respuesta
        mockMvc.perform(put("/api/v1/tasks/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Updated Task"))
                .andExpect(jsonPath("$.description").value("Updated description"))
                .andExpect(jsonPath("$.completed").value(true))
                .andExpect(jsonPath("$.name").value("Juan Pérez"));
    }

    // Test for deleteTask method
    @Test
    void testDeleteTask() throws Exception {
        // Simular el comportamiento del use case
        doNothing().when(deleteTaskUseCase).execute(eq(1L), any(Authentication.class));

        // Realizar la solicitud DELETE y verificar la respuesta
        mockMvc.perform(delete("/api/v1/tasks/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    // Test for getTasksByUserId method
    @Test
    void testGetTasksByUserId() throws Exception {
        // Datos de respuesta esperados
        TaskResponseDTO taskResponseDTO1 = new TaskResponseDTO(1L, "Task 1", "Description of Task 1", false, "Juan Pérez", LocalDateTime.now());
        TaskResponseDTO taskResponseDTO2 = new TaskResponseDTO(2L, "Task 2", "Description of Task 2", true, "Juan Pérez", LocalDateTime.now());

        // Simular el comportamiento del use case
        when(getTasksByUserIdUseCase.execute(eq(1L), any(Authentication.class))).thenReturn(List.of(taskResponseDTO1, taskResponseDTO2));

        // Realizar la solicitud GET y verificar la respuesta
        mockMvc.perform(get("/api/v1/tasks/user/{userId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L));
    }

    // Test for searchTasks method
    @Test
    void testSearchTasks() throws Exception {
        // Datos de respuesta esperados
        TaskResponseDTO taskResponseDTO1 = new TaskResponseDTO(1L, "Task 1", "Description of Task 1", false, "Juan Pérez", LocalDateTime.now());

        // Simular el comportamiento del use case
        when(searchTasksUseCase.execute(eq("Task 1"), eq("Description of Task 1"), eq(false), any(Authentication.class)))
                .thenReturn(List.of(taskResponseDTO1));

        // Realizar la solicitud GET y verificar la respuesta
        mockMvc.perform(get("/api/v1/tasks/search")
                        .param("title", "Task 1")
                        .param("description", "Description of Task 1")
                        .param("completed", "false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    // Test for getTasksCreatedInPeriod method
    @Test
    void testGetTasksCreatedInPeriod() throws Exception {
        // Datos de respuesta esperados
        TaskResponseDTO taskResponseDTO1 = new TaskResponseDTO(1L, "Task 1", "Description of Task 1", false, "Juan Pérez", LocalDateTime.now());

        // Simular el comportamiento del use case
        when(getTasksCreatedInPeriodUseCase.execute(any(LocalDateTime.class), any(LocalDateTime.class), any(Authentication.class)))
                .thenReturn(List.of(taskResponseDTO1));

        // Realizar la solicitud GET y verificar la respuesta
        mockMvc.perform(get("/api/v1/tasks/period")
                        .param("startDate", "2024-12-01T00:00:00")
                        .param("endDate", "2024-12-31T23:59:59"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }
}
