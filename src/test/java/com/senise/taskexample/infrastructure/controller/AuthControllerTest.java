package com.senise.taskexample.infrastructure.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.senise.taskexample.application.dto.request.LoginRequestDTO;
import com.senise.taskexample.application.dto.request.UserRequestDTO;
import com.senise.taskexample.application.dto.response.TokenResponseDTO;
import com.senise.taskexample.application.dto.response.UserResponseDTO;
import com.senise.taskexample.domain.usecase.auth.LoginUserUseCase;
import com.senise.taskexample.domain.usecase.auth.RegisterUserUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegisterUserUseCase registerUserUseCase;

    @MockBean
    private LoginUserUseCase loginUserUseCase;

    @Test
    void register() throws Exception {
        // Crear un DTO de respuesta de usuario simulado
        UserResponseDTO userResponseDTO = new UserResponseDTO(
                1L, 
                "Alice", 
                "alice@example.com", 
                "ROLE_USER", 
                null // No es necesario incluir la fecha en este test
        );

        // Simular el comportamiento de RegisterUserUseCase
        given(registerUserUseCase.execute(any(UserRequestDTO.class))).willReturn(userResponseDTO);

        // Realizar el test del endpoint de registro
        mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Alice\",\"email\":\"alice@example.com\",\"password\":\"password\"}"))
                .andExpect(status().isNoContent()) // Se espera el código de estado 204
                .andExpect(content().string(""))  // No hay contenido en la respuesta
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Alice"))
                .andExpect(jsonPath("$.email").value("alice@example.com"))
                .andExpect(jsonPath("$.role").value("ROLE_USER"));
    }

    @Test
    void login() throws Exception {
        // Crear un DTO de respuesta con el token simulado
        TokenResponseDTO tokenResponse = new TokenResponseDTO(
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWV9.dyt0CoTl4WoVjAHI9Q_CwSKhl6d_9rhM3NrXuJttkao",
                1L
        );

        // Simular el comportamiento de LoginUserUseCase
        given(loginUserUseCase.execute(any(LoginRequestDTO.class))).willReturn(tokenResponse);

        // Realizar el test del endpoint de login
        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"alice@example.com\",\"password\":\"password\"}"))
                .andExpect(status().isOk()) // Se espera el código de estado 200
                .andExpect(jsonPath("$.token").value(tokenResponse.getToken()))
                .andExpect(jsonPath("$.id").value(1));
    }
}
