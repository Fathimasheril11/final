package com.example.BookingApp.controller;

import com.example.BookingApp.entity.TheaterEntity;
import com.example.BookingApp.service.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TheaterController {

    @Autowired
    private TheaterService theaterService;

    @GetMapping("/admin/theaters")
    public String listTheaters(Model model) {
        model.addAttribute("theaters", theaterService.getAllTheaters());
        return "admin/theaters";
    }

    @GetMapping("/admin/theaters/add")
    public String addTheaterForm(Model model) {
        model.addAttribute("theater", new TheaterEntity());
        return "admin/add-theater";
    }

    @PostMapping("/admin/theaters/add")
    public String addTheater(@ModelAttribute TheaterEntity theater) {
        theaterService.addTheater(theater);
        return "redirect:/admin/theaters";
    }

    @GetMapping("/admin/theaters/edit/{id}")
    public String editTheaterForm(@PathVariable Long id, Model model) {
        model.addAttribute("theater", theaterService.getTheaterById(id).orElseThrow());
        return "admin/edit-theater";
    }

    @PostMapping("/admin/theaters/edit/{id}")
    public String updateTheater(@PathVariable Long id, @ModelAttribute TheaterEntity theater) {
        theaterService.updateTheater(id, theater);
        return "redirect:/admin/theaters";
    }

    @PostMapping("/admin/theaters/delete/{id}")
    public String deleteTheater(@PathVariable Long id) {
        theaterService.deleteTheater(id);
        return "redirect:/admin/theaters";
    }
}
