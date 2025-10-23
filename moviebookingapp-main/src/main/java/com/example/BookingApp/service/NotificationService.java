package com.example.BookingApp.service;

import com.example.BookingApp.entity.BookingEntity;
import com.example.BookingApp.entity.SeatEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendConfirmation(BookingEntity booking) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(booking.getUser().getEmail());
        message.setSubject("Booking Confirmation");
        message.setText("Your booking for " + booking.getShow().getMovie().getTitle() +
                " at " + booking.getShow().getShowTime() +
                " is confirmed. Seats: " + booking.getSeats().stream().map(SeatEntity::getSeatNumber).toList() +
                ". Total: $" + booking.getTotalAmount());
        mailSender.send(message);
    }

    public void sendCancellation(BookingEntity booking) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(booking.getUser().getEmail());
        message.setSubject("Booking Cancellation");
        message.setText("Your booking for " + booking.getShow().getMovie().getTitle() + " has been cancelled.");
        mailSender.send(message);
    }
}
