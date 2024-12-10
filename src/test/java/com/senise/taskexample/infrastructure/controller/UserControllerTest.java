package com.senise.taskexample.infrastructure.controller;

import com.senise.taskexample.application.dto.response.UserResponseDTO;
import com.senise.taskexample.application.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    /*@Test
    void testCreateUser() throws Exception {
        // Mockear el Authentication (si es necesario en la lógica de tu servicio)
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("juan.perez@example.com");
    
        // Datos de entrada
        UserRequestDTO userRequestDTO = new UserRequestDTO(
                "Juan Pérez", "juan.perez@example.com", "Abc1234.", "ROLE_USER"
        );
    
        // Datos de respuesta esperados
        UserResponseDTO userResponseDTO = new UserResponseDTO(1L, "Juan Pérez", "juan.perez@example.com", "ROLE_USER", LocalDateTime.now());
    
        // Mockear el servicio
        when(userService.createUser(any(UserRequestDTO.class), any(Authentication.class))).thenReturn(userResponseDTO);
    
        // Realizar la solicitud POST y verificar la respuesta
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Juan Pérez\",\"email\":\"juan.perez@example.com\",\"password\":\"Abc1234.\",\"role\":\"ROLE_USER\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Juan Pérez"))
                .andExpect(jsonPath("$.email").value("juan.perez@example.com"))
                .andExpect(jsonPath("$.role").value("ROLE_USER"));
    }*/
    

    @Test
    void testGetAllUsers() throws Exception {
        UserResponseDTO userResponseDTO = new UserResponseDTO(1L, "Juan Pérez", "juan.perez@example.com", "ROLE_USER", LocalDateTime.now());

        when(userService.getAllUsers()).thenReturn(List.of(userResponseDTO));

        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Juan Pérez"))
                .andExpect(jsonPath("$[0].email").value("juan.perez@example.com"))
                .andExpect(jsonPath("$[0].role").value("ROLE_USER"));
    }

    @Test
    void testGetUserById() throws Exception {
        UserResponseDTO userResponseDTO = new UserResponseDTO(1L, "Juan Pérez", "juan.perez@example.com", "ROLE_USER", LocalDateTime.now());

        when(userService.getUserById(1L, null)).thenReturn(userResponseDTO);

        mockMvc.perform(get("/api/v1/users/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Juan Pérez"))
                .andExpect(jsonPath("$.email").value("juan.perez@example.com"))
                .andExpect(jsonPath("$.role").value("ROLE_USER"));
    }

    /*@Test
    void testUpdateUser() throws Exception {
    
        // Datos de entrada para la solicitud
        UserRequestDTO userRequestDTO = new UserRequestDTO(
                "Juan Pérez Actualizado", "juan.perez.new@example.com", "NewPassword123", "ROLE_ADMIN"
        );
    
        // Datos de respuesta esperados
        UserResponseDTO userResponseDTO = new UserResponseDTO(1L, "Juan Pérez Actualizado", "juan.perez.new@example.com", "ROLE_ADMIN", LocalDateTime.now());
    
        // Mockear la respuesta del servicio
        when(userService.updateUser(eq(1L), any(UserRequestDTO.class), any(Authentication.class))).thenReturn(userResponseDTO);
    
        // Realizar la solicitud PUT y verificar la respuesta
        mockMvc.perform(put("/api/v1/users/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1")) // Verificar que id es un Long
                .andExpect(jsonPath("$.name").value("Juan Pérez Actualizado"))
                .andExpect(jsonPath("$.email").value("juan.perez.new@example.com"))
                .andExpect(jsonPath("$.role").value("ROLE_ADMIN"));
    }*/
    
    

  @Test
void testDeleteUser() throws Exception {
    // Mockear el Authentication (si es necesario en la lógica de tu servicio)
    Authentication authentication = mock(Authentication.class);
    when(authentication.getName()).thenReturn("juan.perez@example.com");

    // Realizar la solicitud DELETE
    mockMvc.perform(delete("/api/v1/users/{id}", 1L)
            .principal(authentication))  // Puedes añadir el principal si es necesario
            .andExpect(status().isNoContent());  // Esperamos un 204 No Content

    // Verificar que el servicio deleteUser se haya llamado una vez con el id correcto
    verify(userService, times(1)).deleteUser(eq(1L), any(Authentication.class));
}

}
