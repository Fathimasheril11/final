package com.example.BookingApp.repository;

import com.example.BookingApp.entity.ShowEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShowRepository extends JpaRepository<ShowEntity, Long> {
    List<ShowEntity> findByMovieId (Long movieId);
}
