package com.senise.taskexample.infrastructure.controller;

import com.senise.taskexample.application.dto.request.UserRequestDTO;
import com.senise.taskexample.application.dto.response.UserResponseDTO;
import com.senise.taskexample.application.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/users")
@AllArgsConstructor
public class UserAdminController {

    private final UserService userService;

    @GetMapping
    public String getAllUsers(Model model) {
        List<UserResponseDTO> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users/index";  // Thymeleaf buscará la vista en src/main/resources/templates/users/index.html
    }

    @GetMapping("/{id}")
    public String getUserById(@PathVariable Long id, Model model) {
        UserResponseDTO user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "users/detail";
    }

    @GetMapping("/create")
    public String createUserForm(Model model) {
        model.addAttribute("user", new UserRequestDTO()); // Para llenar el formulario de creación
        return "users/create";
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute UserRequestDTO userRequestDTO) {
        userService.createUser(userRequestDTO);
        return "redirect:/admin/users";  // Redirige después de crear el usuario
    }
}
