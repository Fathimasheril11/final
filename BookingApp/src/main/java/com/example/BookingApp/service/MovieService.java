package com.example.BookingApp.service;

import com.example.BookingApp.entity.MovieEntity;
import com.example.BookingApp.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public List<MovieEntity> getAllMovies() {
        return movieRepository.findAll();
    }

    public Optional<MovieEntity> getMovieById(Long id) {
        return movieRepository.findById(id);
    }

    public MovieEntity addMovie(MovieEntity movie) {
        return movieRepository.save(movie);
    }

    public MovieEntity updateMovie(Long id, MovieEntity movie) {
        movie.setId(id);
        return movieRepository.save(movie);
    }

    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }

    public List<MovieEntity> searchByGenre(String genre) {
        return movieRepository.findByGenreContaining(genre);
    }

}
