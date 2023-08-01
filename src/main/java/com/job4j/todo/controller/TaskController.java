package com.job4j.todo.controller;

import com.job4j.todo.model.Category;
import com.job4j.todo.model.Task;
import com.job4j.todo.model.User;
import com.job4j.todo.services.CategoryService;
import com.job4j.todo.services.PriorityService;
import com.job4j.todo.services.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final PriorityService priorityService;

    private final CategoryService categoryService;

    @GetMapping
    public String getList(Model model, @RequestParam(required = false) Boolean done) {
        if (done != null) {
            model.addAttribute("tasks", taskService.findAllByDone(done));
        } else {
            model.addAttribute("tasks", taskService.findAll());
        }
        return "tasks/list";
    }

    @GetMapping("/create")
    public String getCreationPage(Model model) {
        model.addAttribute("priorities", priorityService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        return "tasks/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Task task, @SessionAttribute User user, @RequestParam("categoryId") List<Integer> categoryId) {
        task.setUser(user);
        var categories = (List<Category>) categoryService.findByIds(categoryId);
        task.setCategories(categories);
        taskService.create(task);
        return "redirect:/tasks";
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
        if (!taskService.changeStatus(id)) {
            model.addAttribute("error", "Task not found");
            return "errors/404";
        }
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
        model.addAttribute("priorities", priorityService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        return "tasks/update";
    }

    @PostMapping("update")
    public String update(Model model, @ModelAttribute Task task, @SessionAttribute User user, @RequestParam("categoryId") List<Integer> categoryId) {
        var categories = (List<Category>) categoryService.findByIds(categoryId);
        task.setCategories(categories);
        task.setUser(user);
        if (!taskService.update(task)) {
            model.addAttribute("error", "Task not found");
            return "errors/404";
        }
        return "redirect:/tasks";
    }

    @GetMapping("delete/{id}")
    public String delete(Model model, @PathVariable int id) {
        if (!taskService.deleteById(id)) {
            model.addAttribute("error", "Task not found");
            return "errors/404";
        }
        return "redirect:/tasks";
    }
}