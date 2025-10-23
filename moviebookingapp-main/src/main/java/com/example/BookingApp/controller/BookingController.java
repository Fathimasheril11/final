package com.example.BookingApp.controller;

import com.example.BookingApp.entity.BookingEntity;
import com.example.BookingApp.service.BookingService;
import com.example.BookingApp.service.SeatBookingService;
import com.example.BookingApp.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class BookingController {

    @Autowired
    private SeatBookingService seatBookingService;

    @Autowired
    private ShowService showService;

    @Autowired
    private BookingService bookingService;

    @GetMapping("/book/{showId}")
    public String selectSeats(@PathVariable Long showId, Model model) {
        model.addAttribute("show", showService.getShowById(showId).orElseThrow());
        model.addAttribute("availableSeats", seatBookingService.getAvailableSeats(showId));
        return "select-seats";
    }

    @PostMapping("/book/{showId}")
    public String reserve(@PathVariable Long showId, @RequestParam("selectedSeats") List<Integer> selectedSeats) {
        // Assume price per seat is 10.0 for demo
        BookingEntity booking = seatBookingService.reserveSeats(showId, selectedSeats, 10.0);
        return "redirect:/booking/confirmation/" + booking.getId();
    }

    @GetMapping("/booking/confirmation/{id}")
    public String confirmation(@PathVariable Long id, Model model) {
        model.addAttribute("booking", bookingService.getBookingById(id).orElseThrow());
        return "confirmation";
    }

    @PostMapping("/booking/cancel/{id}")
    public String cancel(@PathVariable Long id) {
        bookingService.cancelBooking(id);
        return "redirect:/profile";
    }
}
