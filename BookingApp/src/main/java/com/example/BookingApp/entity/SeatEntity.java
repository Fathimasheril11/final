package com.example.BookingApp.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "seats")
@Data
public class SeatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int seatNumber;
    private boolean available = true;

    @ManyToOne
    private ShowEntity show;

    @ManyToOne
    private BookingEntity booking;
}
