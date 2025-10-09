package com.example.BookingApp.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "theaters")
@Data
public class TheaterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String location;
    private int totalSeats;
}
