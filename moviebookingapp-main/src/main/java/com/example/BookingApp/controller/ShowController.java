package com.example.BookingApp.controller;

import com.example.BookingApp.entity.ShowEntity;
import com.example.BookingApp.service.MovieService;
import com.example.BookingApp.service.ShowService;
import com.example.BookingApp.service.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ShowController {

    @Autowired
    private ShowService showService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private TheaterService theaterService;

    @GetMapping("/shows/{movieId}")
    public String listShowsForMovie(@PathVariable Long movieId, Model model) {
        model.addAttribute("shows", showService.findByMovieId(movieId));
        model.addAttribute("movie", movieService.getMovieById(movieId).orElseThrow());
        return "shows";
    }

    @GetMapping("/admin/shows")
    public String listShows(Model model) {
        model.addAttribute("shows", showService.getAllShows());
        return "admin/shows";
    }

    @GetMapping("/admin/shows/add")
    public String addShowForm(Model model) {
        model.addAttribute("show", new ShowEntity());
        model.addAttribute("movies", movieService.getAllMovies());
        model.addAttribute("theaters", theaterService.getAllTheaters());
        return "admin/add-show";
    }

    @PostMapping("/admin/shows/add")
    public String addShow(@ModelAttribute ShowEntity show) {
        showService.addShow(show);
        return "redirect:/admin/shows";
    }

    @GetMapping("/admin/shows/edit/{id}")
    public String editShowForm(@PathVariable Long id, Model model) {
        model.addAttribute("show", showService.getShowById(id).orElseThrow());
        model.addAttribute("movies", movieService.getAllMovies());
        model.addAttribute("theaters", theaterService.getAllTheaters());
        return "admin/edit-show";
    }

    @PostMapping("/admin/shows/edit/{id}")
    public String updateShow(@PathVariable Long id, @ModelAttribute ShowEntity show) {
        showService.updateShow(id, show);
        return "redirect:/admin/shows";
    }

    @PostMapping("/admin/shows/delete/{id}")
    public String deleteShow(@PathVariable Long id) {
        showService.deleteShow(id);
        return "redirect:/admin/shows";
    }
}
