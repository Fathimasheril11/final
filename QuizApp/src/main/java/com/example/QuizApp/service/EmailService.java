package com.example.QuizApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromEmail; // Sender email from application.properties

    public void sendQuizResultEmail(String to, String quizTitle, int score, int total) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail); // e.g., quizapp@example.com
        message.setTo(to);
        message.setSubject("Your Quiz Results: " + quizTitle);
        message.setText(String.format(
                "Dear Participant,\n\n" +
                        "Congratulations on completing the quiz: %s!\n\n" +
                        "Your Score: %d out of %d (%.1f%%)\n\n" +
                        "Keep up the great work! If you have any questions, feel free to contact us.\n\n" +
                        "Best regards,\n" +
                        "Online Quiz App Team",
                quizTitle,
                score,
                total,
                (double) score / total * 100
        ));

        javaMailSender.send(message);
    }

    public void sendQuizResultEmailHtml(String to, String quizTitle, int score, int total) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(to);
        message.setSubject("Your Quiz Results: " + quizTitle);

        String htmlBody = String.format(
                "<html><body>" +
                        "<h2>Quiz Results</h2>" +
                        "<p><strong>Quiz:</strong> %s</p>" +
                        "<p><strong>Score:</strong> %d / %d (%.1f%%)</p>" +
                        "<p>Congratulations! Keep learning.</p>" +
                        "<p>Best,<br>Quiz App Team</p>" +
                        "</body></html>",
                quizTitle,
                score,
                total,
                (double) score / total * 100
        );

        message.setText(htmlBody);

        javaMailSender.send(message);
    }
}
