package com.example.BookingApp.repository;

import com.example.BookingApp.entity.SeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<SeatEntity, Long> {
    List<SeatEntity> findByShowIdAndSeatNumberInAndAvailable (Long showId, List<Integer> seatNumber, boolean available);
    List<SeatEntity> findByShowIdAndAvailable (Long showId, boolean available);
}
