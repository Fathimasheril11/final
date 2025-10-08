package com.example.QuizApp.controller;

import com.example.QuizApp.entity.User;
import com.example.QuizApp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home() {
        return "redirect:/login"; // Or a landing page
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            return "auth/register";
        }
        if (userService.findByUsername(user.getUsername()) != null) {
            bindingResult.rejectValue("username", "error.username", "Username already exists");
            return "auth/register";
        }
        user.setRole("PARTICIPANT");
        userService.save(user);
        return "redirect:/login?success=Registration successful! Please login.";
    }

    @GetMapping("/login")
    public String showLogin(Model model) {
        return "auth/login";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "error/access-denied"; // Add a simple template
    }
}
