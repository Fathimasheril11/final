package com.example.BookingApp.service;

import com.example.BookingApp.entity.BookingEntity;
import com.example.BookingApp.entity.SeatEntity;
import com.example.BookingApp.entity.ShowEntity;
import com.example.BookingApp.repository.SeatRepository;
import com.example.BookingApp.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SeatBookingService {

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private ShowRepository showRepository;

    @Transactional
    public BookingEntity reserveSeats(Long showId, List<Integer> seatNumbers, double pricePerSeat) {
        List<SeatEntity> seats = seatRepository.findByShowIdAndSeatNumberInAndAvailable(showId, seatNumbers, true);
        if (seats.size() != seatNumbers.size()) {
            throw new RuntimeException("Some seats are already booked or invalid");
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        // Assume UserService has findByUsername
//         UserEntity user = userService.findByUsername(username).orElseThrow();

        ShowEntity show = showRepository.findById(showId).orElseThrow(() -> new RuntimeException("Show not found"));

        BookingEntity booking = new BookingEntity();
        booking.setShow(show);
//         booking.setUser(user);
        booking.setSeats(seats);
        booking.setTotalAmount(seatNumbers.size() * pricePerSeat); // Example pricing

        seats.forEach(seat -> {
            seat.setAvailable(false);
            seat.setBooking(booking);
        });

        bookingService.createBooking(booking);
        seatRepository.saveAll(seats);

        return booking;
    }

    public List<SeatEntity> getAvailableSeats(Long showId) {
        return seatRepository.findByShowIdAndAvailable(showId, true);
    }
}
