package com.example.BookingApp.repository;

import com.example.BookingApp.entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<MovieEntity, Long> {
    List<MovieEntity> findByGenreContaining (String genre);
}
