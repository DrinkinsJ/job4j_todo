package com.job4j.todo.controller;

import com.job4j.todo.model.Task;
import com.job4j.todo.services.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public String getList(Model model, @RequestParam(required= false) Boolean done) {
        if (done != null) {
            model.addAttribute("tasks", taskService.findAllByDone(done));
        } else {
            model.addAttribute("tasks", taskService.findAll());
        }
        return "tasks/list";
    }

    @GetMapping("/create")
    public String getCreationPage() {
        return "tasks/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Task task, Model model) {
        try {
            taskService.create(task);
            return "redirect:/tasks";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "errors/404";
        }
    }

    @GetMapping("/{id}")
    public String getOneTask(Model model, @PathVariable int id) {
        var taskOptional = taskService.findById(id);
        if (taskOptional.isEmpty()) {
            model.addAttribute("error", "Task not found");
            return "errors/404";
        }
        model.addAttribute("task", taskOptional.get());
        return "tasks/one";
    }

    @GetMapping("/changeStatus/{id}")
    public String changeStatus(Model model, @PathVariable int id) {
        Optional<Task> taskOptional = taskService.findById(id);
        if (taskOptional.isEmpty()) {
            model.addAttribute("error", "Task not found");
            return "errors/404";
        }
        Task task = taskOptional.get();
        task.setDone(!task.isDone());
        taskService.update(task);
        return "redirect:/tasks/{id}";
    }

    @GetMapping("update/{id}")
    public String getUpdate(Model model, @PathVariable int id) {
        Optional<Task> taskOptional = taskService.findById(id);
        if (taskOptional.isEmpty()) {
            model.addAttribute("error", "Task not found");
            return "errors/404";
        }
        model.addAttribute("task", taskOptional.get());
        return "tasks/update";
    }

    @PostMapping("update")
    public String update(Model model, @ModelAttribute Task task) {
        try {
            taskService.update(task);
            return "redirect:/tasks";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "errors/404";
        }
    }

    @GetMapping("delete/{id}")
    public String delete(Model model, @PathVariable int id) {
        try {
            taskService.deleteById(id);
            return "redirect:/tasks";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "errors/404";
        }
    }
}
