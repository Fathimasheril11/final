package com.example.BookingApp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "bookings")
@Data
public class BookingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private UserEntity user;

    @ManyToOne
    private ShowEntity show;

    @OneToMany(mappedBy = "booking")
    private List<SeatEntity> seats;

    private LocalDateTime bookingTime;
    private String paymentStatus; // "PENDING", "PAID", "CANCELLED"
    private double totalAmount;
}
