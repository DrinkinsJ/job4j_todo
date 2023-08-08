package com.job4j.todo.controller;

import com.job4j.todo.model.Task;
import com.job4j.todo.model.User;
import com.job4j.todo.services.CategoryService;
import com.job4j.todo.services.PriorityService;
import com.job4j.todo.services.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import static com.job4j.todo.utils.TimeUtils.addUserTimeZone;


@Controller
@AllArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    private final PriorityService priorityService;


    private final CategoryService categoryService;

    @GetMapping
    public String getList(Model model, @RequestParam(required = false) Boolean done, HttpSession httpSession) {
        Collection<Task> tasks;
        var user = (User) httpSession.getAttribute("user");
        if (done != null) {
            tasks = taskService.findAllByDone(done);
            tasks.forEach(task -> addUserTimeZone(user, task));
            model.addAttribute("tasks", tasks);
        } else {
            tasks = taskService.findAll();
            tasks.forEach(task -> addUserTimeZone(user, task));
            model.addAttribute("tasks", tasks);
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
    public String create(@ModelAttribute Task task, @SessionAttribute User user, @RequestParam("categoryId") Set<Integer> categoryId) {
        task.setUser(user);
        task.setCategories(categoryService.findByIds(categoryId));
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
    public String update(Model model, @ModelAttribute Task task, @SessionAttribute User user, @RequestParam("categoryId") Set<Integer> categoryId) {
        task.setCategories(categoryService.findByIds(categoryId));
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