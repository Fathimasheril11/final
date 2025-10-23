package com.example.BookingApp.service;

import com.example.BookingApp.entity.ShowEntity;
import com.example.BookingApp.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShowService {

    @Autowired
    private ShowRepository showRepository;

    public List<ShowEntity> getAllShows() {
        return showRepository.findAll();
    }

    public Optional<ShowEntity> getShowById(Long id) {
        return showRepository.findById(id);
    }

    public ShowEntity addShow(ShowEntity show) {
        return showRepository.save(show);
    }

    public ShowEntity updateShow(Long id, ShowEntity show) {
        show.setId(id);
        return showRepository.save(show);
    }

    public void deleteShow(Long id) {
        showRepository.deleteById(id);
    }

    public List<ShowEntity> findByMovieId(Long movieId) {
        return showRepository.findByMovieId(movieId);
    }
}
