package com.example.BookingApp.controller;

import com.example.BookingApp.entity.MovieEntity;
import com.example.BookingApp.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/movies")
    public String listMovies(Model model, @RequestParam(required = false) String genre) {
        if (genre != null && !genre.isEmpty()) {
            model.addAttribute("movies", movieService.searchByGenre(genre));
        } else {
            model.addAttribute("movies", movieService.getAllMovies());
        }
        return "movies";
    }

    @GetMapping("/admin/movies/add")
    public String addMovieForm(Model model) {
        model.addAttribute("movie", new MovieEntity());
        return "admin/add-movie";
    }

    @PostMapping("/admin/movies/add")
    public String addMovie(@ModelAttribute MovieEntity movie) {
        movieService.addMovie(movie);
        return "redirect:/movies";
    }

    @GetMapping("/admin/movies/edit/{id}")
    public String editMovieForm(@PathVariable Long id, Model model) {
        model.addAttribute("movie", movieService.getMovieById(id).orElseThrow());
        return "admin/edit-movie";
    }

    @PostMapping("/admin/movies/edit/{id}")
    public String updateMovie(@PathVariable Long id, @ModelAttribute MovieEntity movie) {
        movieService.updateMovie(id, movie);
        return "redirect:/movies";
    }

    @PostMapping("/admin/movies/delete/{id}")
    public String deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return "redirect:/movies";
    }
}
