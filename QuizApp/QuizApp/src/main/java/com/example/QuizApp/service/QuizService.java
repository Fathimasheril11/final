package com.example.QuizApp.service;

import com.example.QuizApp.entity.Quiz;
import com.example.QuizApp.entity.Result;

import java.util.List;
import java.util.Map;

public interface QuizService {

    Quiz save(Quiz quiz);
    List<Quiz> findAll();
    Quiz findById(Long id);
    Result submitQuiz(Long quizId, Long userId, Map<Long, String> userAnswers); // Map question index to answer
    List<Result> getUserResults(Long userId);

    void delete(Quiz quiz);

}
