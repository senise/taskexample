package com.senise.taskexample.application.service.impl;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.senise.taskexample.application.dto.request.UserRequestDTO;
import com.senise.taskexample.application.dto.response.UserResponseDTO;
import com.senise.taskexample.domain.usecase.user.*;
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
public class UserServiceImplTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private CreateUserUseCase createUserUseCase;

    @Mock
    private GetAllUsersUseCase getAllUsersUseCase;

    @Mock
    private GetUserByIdUseCase getUserByIdUseCase;

    @Mock
    private UpdateUserUseCase updateUserUseCase;

    @Mock
    private DeleteUserUseCase deleteUserUseCase;

    @Mock
    private SearchUsersUseCase searchUsersUseCase;

    @Mock
    private GetUsersCreatedInPeriodUseCase getUsersCreatedInPeriodUseCase;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test for createUser method
    @Test
    void testCreateUser() throws Exception {
        // Datos de entrada
        UserRequestDTO userRequestDTO = new UserRequestDTO("Juan Pérez", "juan@example.com", "password", "USER");

        // Datos de respuesta esperados
        UserResponseDTO userResponseDTO = new UserResponseDTO(1L, "Juan Pérez", "juan@example.com", "USER", LocalDateTime.now());

        // Simular el comportamiento del use case
        when(createUserUseCase.execute(any(UserRequestDTO.class), any(Authentication.class))).thenReturn(userResponseDTO);

        // Realizar la solicitud POST y verificar la respuesta
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Juan Pérez"))
                .andExpect(jsonPath("$.email").value("juan@example.com"))
                .andExpect(jsonPath("$.role").value("USER"));
    }

    // Test for getAllUsers method
    @Test
    void testGetAllUsers() throws Exception {
        // Datos de respuesta esperados
        UserResponseDTO userResponseDTO1 = new UserResponseDTO(1L, "Juan Pérez", "juan@example.com", "USER", LocalDateTime.now());
        UserResponseDTO userResponseDTO2 = new UserResponseDTO(2L, "Ana García", "ana@example.com", "ADMIN", LocalDateTime.now());

        // Simular el comportamiento del use case
        when(getAllUsersUseCase.execute()).thenReturn(List.of(userResponseDTO1, userResponseDTO2));

        // Realizar la solicitud GET y verificar la respuesta
        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L));
    }

    // Test for getUserById method
    @Test
    void testGetUserById() throws Exception {
        // Datos de respuesta esperados
        UserResponseDTO userResponseDTO = new UserResponseDTO(1L, "Juan Pérez", "juan@example.com", "USER", LocalDateTime.now());

        // Simular el comportamiento del use case
        when(getUserByIdUseCase.execute(eq(1L), any(Authentication.class))).thenReturn(userResponseDTO);

        // Realizar la solicitud GET y verificar la respuesta
        mockMvc.perform(get("/api/v1/users/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Juan Pérez"))
                .andExpect(jsonPath("$.email").value("juan@example.com"))
                .andExpect(jsonPath("$.role").value("USER"));
    }

    // Test for updateUser method
    @Test
    void testUpdateUser() throws Exception {
        // Datos de entrada
        UserRequestDTO userRequestDTO = new UserRequestDTO("Juan Pérez Actualizado", "juan_updated@example.com", "newpassword", "ADMIN");

        // Datos de respuesta esperados
        UserResponseDTO userResponseDTO = new UserResponseDTO(1L, "Juan Pérez Actualizado", "juan_updated@example.com", "ADMIN", LocalDateTime.now());

        // Simular el comportamiento del use case
        when(updateUserUseCase.execute(eq(1L), any(UserRequestDTO.class), any(Authentication.class))).thenReturn(userResponseDTO);

        // Realizar la solicitud PUT y verificar la respuesta
        mockMvc.perform(put("/api/v1/users/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Juan Pérez Actualizado"))
                .andExpect(jsonPath("$.email").value("juan_updated@example.com"))
                .andExpect(jsonPath("$.role").value("ADMIN"));
    }

    // Test for deleteUser method
    @Test
    void testDeleteUser() throws Exception {
        // Simular el comportamiento del use case
        doNothing().when(deleteUserUseCase).execute(eq(1L), any(Authentication.class));

        // Realizar la solicitud DELETE y verificar la respuesta
        mockMvc.perform(delete("/api/v1/users/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    // Test for searchUsers method
    @Test
    void testSearchUsers() throws Exception {
        // Datos de respuesta esperados
        UserResponseDTO userResponseDTO1 = new UserResponseDTO(1L, "Juan Pérez", "juan@example.com", "USER", LocalDateTime.now());

        // Simular el comportamiento del use case
        when(searchUsersUseCase.execute(eq("Juan Pérez"), eq("juan@example.com"), eq("USER"))).thenReturn(List.of(userResponseDTO1));

        // Realizar la solicitud GET y verificar la respuesta
        mockMvc.perform(get("/api/v1/users/search")
                        .param("name", "Juan Pérez")
                        .param("email", "juan@example.com")
                        .param("role", "USER"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    // Test for getUsersCreatedInPeriod method
    @Test
    void testGetUsersCreatedInPeriod() throws Exception {
        // Datos de respuesta esperados
        UserResponseDTO userResponseDTO1 = new UserResponseDTO(1L, "Juan Pérez", "juan@example.com", "USER", LocalDateTime.now());

        // Simular el comportamiento del use case
        when(getUsersCreatedInPeriodUseCase.execute(any(LocalDateTime.class), any(LocalDateTime.class), any(Authentication.class)))
                .thenReturn(List.of(userResponseDTO1));

        // Realizar la solicitud GET y verificar la respuesta
        mockMvc.perform(get("/api/v1/users/period")
                        .param("startDate", "2024-12-01T00:00:00")
                        .param("endDate", "2024-12-31T23:59:59"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }
}
