package com.example.QuizApp.controller;

import com.example.QuizApp.entity.User;
import com.example.QuizApp.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

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

    @PostMapping("/login")
    public String login(@ModelAttribute("username") String username,
                        @ModelAttribute("password") String password,
                        HttpServletRequest request,
                        HttpServletResponse response,
                        Model model) {
        try {
            // Create authentication token
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
            authToken.setDetails(new WebAuthenticationDetails(request));

            // Authenticate using manager
            Authentication authentication = authenticationManager.authenticate(authToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // On success, redirect based on role (custom logic)
            User user = userService.findByUsername(username);
            if ("ADMIN".equals(user.getRole())) {
                return "redirect:/admin/dashboard";
            } else {
                return "redirect:/participant/dashboard";
            }
        } catch (AuthenticationException e) {
            model.addAttribute("error", "Invalid username or password");
            return "auth/login";
        }
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "error/access-denied"; // Add a simple template
    }
}
