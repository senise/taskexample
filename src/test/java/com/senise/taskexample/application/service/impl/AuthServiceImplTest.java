package com.senise.taskexample.application.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senise.taskexample.application.dto.request.LoginRequestDTO;
import com.senise.taskexample.application.dto.request.UserRequestDTO;
import com.senise.taskexample.application.dto.response.TokenResponseDTO;
import com.senise.taskexample.application.dto.response.UserResponseDTO;
import com.senise.taskexample.domain.usecase.auth.LoginUserUseCase;
import com.senise.taskexample.domain.usecase.auth.RegisterUserUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.matchesPattern;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthServiceImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegisterUserUseCase registerUserUseCase;

    @MockBean
    private LoginUserUseCase loginUserUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testRegister() throws Exception {
        // Datos de entrada
        UserRequestDTO userRequestDTO = new UserRequestDTO(
                "Juan Pérez", "juan.perez@example.com", "Abc1234.", "ROLE_USER"
        );

        // Datos de respuesta esperados
        LocalDateTime now = LocalDateTime.now(); // Capturar el momento actual
        UserResponseDTO userResponseDTO = new UserResponseDTO(1L, "Juan Pérez", "juan.perez@example.com", "ROLE_USER", now);

        // Simular el comportamiento del use case
        when(registerUserUseCase.execute(any(UserRequestDTO.class))).thenReturn(userResponseDTO);

        // Realizar la solicitud POST y verificar la respuesta
        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Juan Pérez"))
                .andExpect(jsonPath("$.email").value("juan.perez@example.com"))
                .andExpect(jsonPath("$.role").value("ROLE_USER"))
                // Verificar que el createdAt esté cerca de la hora actual
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.createdAt").value(matchesPattern("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}")));
    }


    @Test
    void testLogin() throws Exception {
        // Datos de entrada
        LoginRequestDTO loginRequest = new LoginRequestDTO("juan.perez@example.com", "Abc1234.");

        // Datos de respuesta esperados
        TokenResponseDTO tokenResponse = new TokenResponseDTO("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWV9.dyt0CoTl4WoVjAHI9Q_CwSKhl6d_9rhM3NrXuJttkao", 1L);

        // Mockear el servicio de login
        when(loginUserUseCase.execute(any(LoginRequestDTO.class))).thenReturn(tokenResponse);

        // Realizar la solicitud POST y verificar la respuesta
        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWV9.dyt0CoTl4WoVjAHI9Q_CwSKhl6d_9rhM3NrXuJttkao"))
                .andExpect(jsonPath("$.id").value(1L));
    }

}
