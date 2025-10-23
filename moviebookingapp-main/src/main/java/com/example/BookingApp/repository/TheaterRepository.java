package com.example.BookingApp.repository;

import com.example.BookingApp.entity.TheaterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TheaterRepository extends JpaRepository<TheaterEntity, Long> {
}
