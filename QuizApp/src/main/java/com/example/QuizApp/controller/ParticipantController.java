package com.example.QuizApp.controller;

import com.example.QuizApp.entity.Quiz;
import com.example.QuizApp.entity.Result;
import com.example.QuizApp.entity.User;
import com.example.QuizApp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/participant")
@PreAuthorize("hasRole('PARTICIPANT')")
public class ParticipantController {

    @Autowired
    private QuizService quizService;

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication auth) {
        model.addAttribute("quizzes", quizService.findAll());
        return "participant/dashboard";
    }

    @GetMapping("/take-quiz/{id}")
    public String takeQuiz(@PathVariable Long id, Model model, Authentication auth) {
        Quiz quiz = quizService.findById(id);
        if (quiz == null) {
            return "redirect:/participant/dashboard?error=Quiz not found";
        }
        model.addAttribute("quiz", quiz);
        return "participant/take-quiz";
    }

    @PostMapping("/submit-quiz/{id}")
    public String submitQuiz(@PathVariable Long id, @RequestParam Map<String, String> paramAnswers, Authentication auth) {
        // Convert paramAnswers (e.g., "answer_1": "A") to Map<Long, String>
        Map<Long, String> userAnswers = new HashMap<>();
        paramAnswers.forEach((key, value) -> {
            if (key.startsWith("answer_")) {
                Long qId = Long.parseLong(key.substring(7));
                userAnswers.put(qId, value);
            }
        });
        Long userId = ((User) auth.getPrincipal()).getId();
        Result result = quizService.submitQuiz(id, userId, userAnswers);
        return "redirect:/participant/results/" + result.getId() + "?success=Quiz submitted! Check your score.";
    }

    @GetMapping("/results/{id}")
    public String viewResult(@PathVariable Long id, Model model, Authentication auth) {
        // Add check: ensure result belongs to user
        Result result = (Result) quizService.getUserResults(viewResult(id));
        if (result == null) {
            return "redirect:/participant/dashboard?error=Result not found";
        }
        model.addAttribute("result", result);
        return "participant/results";
    }

    private Long viewResult(Long id) {
        return null;
    }

    @GetMapping("/past-results")
    public String pastResults(Authentication auth, Model model) {
        Long userId = ((User) auth.getPrincipal()).getId();
        List<Result> results = quizService.getUserResults(userId);
        model.addAttribute("results", results);
        return "participant/past-results";
    }
}
