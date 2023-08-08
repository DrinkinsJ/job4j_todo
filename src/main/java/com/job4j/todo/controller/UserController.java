package com.job4j.todo.controller;

import com.job4j.todo.model.User;
import com.job4j.todo.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.TimeZone;

import static com.job4j.todo.utils.TimeUtils.*;

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
    public String getRegisterPage(Model model) {
        List<TimeZone> timeZones = getTimeZones();
        model.addAttribute("timeZones", timeZones);
        return "users/register";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/users/login";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user, @RequestParam("timezone") String timeZone) {
        user.setTimeZone(Objects.requireNonNullElse(timeZone, "UTC"));
        userService.save(user);
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
