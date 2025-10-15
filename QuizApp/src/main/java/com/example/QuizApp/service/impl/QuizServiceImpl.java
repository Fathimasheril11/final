package com.example.QuizApp.service.impl;

import com.example.QuizApp.entity.*;
import com.example.QuizApp.repository.*;
import com.example.QuizApp.service.EmailService;
import com.example.QuizApp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class QuizServiceImpl implements QuizService {

    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private ResultRepository resultRepository;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;

    @Override
    public Quiz save(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    @Override
    public List<Quiz> findAll() {
        return quizRepository.findAll();
    }

    @Override
    public Quiz findById(Long id) {
        return quizRepository.findById(id).orElse(null);
    }

    @Override
    public Result submitQuiz(Long quizId, Long userId, Map<Long, String> userAnswers) {
        Quiz quiz = findById(quizId);
        User user = userRepository.findById(userId).orElseThrow();
        Result result = new Result();
        result.setUser(user);
        result.setQuiz(quiz);
        int score = 0;

        for (Question q : quiz.getQuestions()) {
            Answer answer = new Answer();
            answer.setQuestion(q);
            answer.setResult(result);
            String selected = userAnswers.get(q.getId());
            answer.setSelectedOption(selected);
            answerRepository.save(answer);

            if (q.getCorrectOption().equals(selected)) {
                score++;
            }
        }

        result.setScore(score);
        result = resultRepository.save(result);

        // Email notification
        emailService.sendQuizResultEmail(user.getEmail(), quiz.getTitle(), score, quiz.getQuestions().size());

        return result;
    }

    @Override
    public List<Result> getUserResults(Long userId) {
        return resultRepository.findByUserId(userId);
    }

    @Override
    public void delete(Quiz quiz) {
        quizRepository.delete(quiz);
    }


}
