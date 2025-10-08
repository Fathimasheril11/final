package com.example.BookingApp.controller;

import com.example.BookingApp.entity.UserEntity;
import com.example.BookingApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new UserEntity());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute UserEntity user) {
        userService.register(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(Model model, @RequestParam(value = "error", required = false) String error) {
        if (error != null) {
            model.addAttribute("error", "Invalid username or password.");
        }
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String username, @RequestParam String password, RedirectAttributes redirectAttributes) {
        try {
            UsernamePasswordAuthenticationToken authRequest =
                    new UsernamePasswordAuthenticationToken(username, password);

            Authentication authentication = authenticationManager.authenticate(authRequest);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            boolean isAdmin = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .anyMatch(role -> role.equals("ADMIN"));

            if (isAdmin) {
                redirectAttributes.addFlashAttribute("success", "Welcome, Admin!");
                return "redirect:/admin";
            } else {
                redirectAttributes.addFlashAttribute("success", "Welcome back!");
                return "redirect:/movies";
            }
        } catch (AuthenticationException e) {

            redirectAttributes.addFlashAttribute("error", "Invalid username or password.");
            return "redirect:/login?error=true";
        }
    }

    @GetMapping("/profile")
    public String profile(Model model, @AuthenticationPrincipal UserEntity user) {
        model.addAttribute("bookings", userService.getBookingHistory(user.getId()));
        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute UserEntity updatedUser, @AuthenticationPrincipal UserEntity user) {
        updatedUser.setId(user.getId());
        userService.updateProfile(updatedUser);
        return "redirect:/profile";
    }
}
