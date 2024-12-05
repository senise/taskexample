package com.senise.taskexample.infrastructure.controller;

import com.senise.taskexample.application.dto.request.TaskRequestDTO;
import com.senise.taskexample.application.dto.response.TaskResponseDTO;
import com.senise.taskexample.application.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/tasks")
@AllArgsConstructor
public class TaskAdminController {

    private final TaskService taskService;

    @GetMapping("/index")
    public String showTasksIndex(Model model) {
        List<TaskResponseDTO> tasks = taskService.getAllTasks(); // Lógica para obtener las tareas
        model.addAttribute("tasks", tasks);
        return "index"; // Esta es la plantilla Thymeleaf
    }

    @GetMapping("/detail/{id}")
    public String getTaskById(@PathVariable Long id, Model model) {
        TaskResponseDTO task = taskService.getTaskById(id);
        model.addAttribute("task", task);
        return "tasks/detail";
    }

    @GetMapping("/create")
    public String createTaskForm(Model model) {
        model.addAttribute("task", new TaskRequestDTO()); // Para llenar el formulario de creación
        return "tasks/create";
    }

    @PostMapping("/create")
    public String createTask(@ModelAttribute TaskRequestDTO taskRequestDTO) {
        taskService.createTask(taskRequestDTO);
        return "redirect:/admin/tasks";  // Redirige después de crear la tarea
    }
}
