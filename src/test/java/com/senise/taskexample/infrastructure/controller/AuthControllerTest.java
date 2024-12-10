package com.senise.taskexample.infrastructure.controller;

import com.senise.taskexample.application.dto.request.LoginRequestDTO;
import com.senise.taskexample.application.dto.request.UserRequestDTO;
import com.senise.taskexample.application.dto.response.TokenResponseDTO;
import com.senise.taskexample.application.dto.response.UserResponseDTO;
import com.senise.taskexample.application.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @MockBean
    private AuthService authService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void register() throws Exception {
        // Crear un DTO de respuesta de usuario simulado
        UserResponseDTO userResponseDTO = new UserResponseDTO(
                1L,
                "Alice",
                "alice@example.com",
                "ROLE_USER",
                null
        );

        // Simular el comportamiento del AuthService
        given(authService.register(any(UserRequestDTO.class))).willReturn(userResponseDTO);

        // Realizar el test
        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Alice\",\"email\":\"alice@example.com\",\"password\":\"password\",\"role\":\"ROLE_USER\"}"))
                .andExpect(status().isCreated()) // Verifica el código 201
                .andExpect(jsonPath("$.id").value(1)) // Verifica el campo "id" en la respuesta
                .andExpect(jsonPath("$.name").value("Alice")) // Verifica el campo "name" en la respuesta
                .andExpect(jsonPath("$.email").value("alice@example.com")) // Verifica el campo "email" en la respuesta
                .andExpect(jsonPath("$.role").value("ROLE_USER")); // Verifica que el campo "role" es "ROLE_USER"
    }

    @Test
    void login() throws Exception {
        // Crear un DTO de respuesta con el token simulado
        TokenResponseDTO tokenResponse = new TokenResponseDTO(
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWV9.dyt0CoTl4WoVjAHI9Q_CwSKhl6d_9rhM3NrXuJttkao",
                1L
        );

        // Simular el comportamiento del AuthService
        given(authService.login(any(LoginRequestDTO.class))).willReturn(tokenResponse);

        // Realizar el test
        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"alice@example.com\",\"password\":\"password\"}"))
                .andExpect(status().isOk()) // Verifica el código 200
                .andExpect(jsonPath("$.token").value(tokenResponse.getToken()))
                .andExpect(jsonPath("$.id").value(1));
    }
}
