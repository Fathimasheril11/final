package com.example.BookingApp.service;

import com.example.BookingApp.entity.BookingEntity;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    public void simulatePayment(BookingEntity booking) {
        booking.setPaymentStatus("PAID");
    }
}
