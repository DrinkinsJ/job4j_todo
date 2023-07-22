package com.job4j.todo.controller;

import com.job4j.todo.model.User;
import com.job4j.todo.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public String getLoginPage() {
        return "users/login";
    }

    @GetMapping("/register")
    public String getRegisterPage() {
        return "users/register";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/users/login";
    }

    @PostMapping("/register")
    public String register(Model model, @ModelAttribute User user) {
        Optional<User> userOptional = userService.save(user);
        if (userOptional.isEmpty()) {
            model.addAttribute("error", "User with this email already exists");
            return "user/register";
        }
        return "redirect:/users/login";
    }

    @PostMapping("/login")
    public String login(Model model, @ModelAttribute User user, HttpSession httpSession) {
        Optional<User> userOptional = userService.findByLoginAndPassword(user.getLogin(), user.getPassword());
        if (userOptional.isEmpty()) {
            model.addAttribute("error", "Incorrect login or password");
            return "users/login";
        }
        httpSession.setAttribute("user", userOptional.get());
        return "redirect:/tasks";
    }
}
