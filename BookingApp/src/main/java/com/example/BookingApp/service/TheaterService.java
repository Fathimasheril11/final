package com.example.BookingApp.service;

import com.example.BookingApp.entity.TheaterEntity;
import com.example.BookingApp.repository.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TheaterService {

    @Autowired
    private TheaterRepository theaterRepository;

    public List<TheaterEntity> getAllTheaters() {
        return theaterRepository.findAll();
    }

    public Optional<TheaterEntity> getTheaterById(Long id) {
        return theaterRepository.findById(id);
    }

    public TheaterEntity addTheater(TheaterEntity theater) {
        return theaterRepository.save(theater);
    }

    public TheaterEntity updateTheater(Long id, TheaterEntity theater) {
        theater.setId(id);
        return theaterRepository.save(theater);
    }

    public void deleteTheater(Long id) {
        theaterRepository.deleteById(id);
    }
}
