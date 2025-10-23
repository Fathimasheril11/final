package com.example.BookingApp.service;

import com.example.BookingApp.entity.BookingEntity;
import com.example.BookingApp.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private NotificationService notificationService;

    @Transactional
    public BookingEntity createBooking(BookingEntity booking) {
        booking.setBookingTime(LocalDateTime.now());
        booking.setPaymentStatus("PENDING");
        BookingEntity savedBooking = bookingRepository.save(booking);
        paymentService.simulatePayment(savedBooking);
        if ("PAID".equals(savedBooking.getPaymentStatus())) {
            notificationService.sendConfirmation(savedBooking);
        }
        return savedBooking;
    }

    public Optional<BookingEntity> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }

    public List<BookingEntity> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Transactional
    public void cancelBooking(Long id) {
        BookingEntity booking = bookingRepository.findById(id).orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setPaymentStatus("CANCELLED");
        booking.getSeats().forEach(seat -> {
            seat.setAvailable(true);
            seat.setBooking(null);
        });
        bookingRepository.save(booking);
        notificationService.sendCancellation(booking);
    }
}
