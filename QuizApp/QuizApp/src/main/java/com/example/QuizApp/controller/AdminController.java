package com.example.QuizApp.controller;

import com.example.QuizApp.entity.Question;
import com.example.QuizApp.entity.Quiz;
import com.example.QuizApp.service.QuizService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private QuizService quizService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("quizzes", quizService.findAll());
        return "admin/dashboard";
    }

    @GetMapping("/create-quiz")
    public String showCreateQuiz(Model model) {
        Quiz quiz = new Quiz();
        quiz.setQuestions(new ArrayList<>()); // Initialize empty list
        model.addAttribute("quiz", quiz);
        return "admin/create-quiz";
    }

    @PostMapping("/create-quiz")
    public String createQuiz(@Valid @ModelAttribute Quiz quiz, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "admin/create-quiz";
        }
        // Handle questions from form (in real impl, parse from request params)
        quizService.save(quiz);
        return "redirect:/admin/dashboard?success=Quiz created successfully!";
    }

    @GetMapping("/edit-quiz/{id}")
    public String showEditQuiz(@PathVariable Long id, Model model) {
        Quiz quiz = quizService.findById(id);
        if (quiz == null) {
            return "redirect:/admin/dashboard?error=Quiz not found";
        }
        model.addAttribute("quiz", quiz);
        return "admin/edit-quiz"; // Similar to create-quiz.html, pre-populated
    }

    @PostMapping("/update-quiz/{id}")
    public String updateQuiz(@PathVariable Long id, @ModelAttribute Quiz quiz) {
        quiz.setId(id);
        quizService.save(quiz);
        return "redirect:/admin/dashboard?success=Quiz updated successfully!";
    }

    @PostMapping("/delete-quiz/{id}")
    public String deleteQuiz(@PathVariable Long id) {
        Quiz quiz = quizService.findById(id);
        if (quiz != null) {
            quizService.delete(quiz); // Add delete method to service
        }
        return "redirect:/admin/dashboard?success=Quiz deleted successfully!";
    }
}
