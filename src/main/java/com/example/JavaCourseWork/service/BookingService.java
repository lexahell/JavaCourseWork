package com.example.JavaCourseWork.service;

import com.example.JavaCourseWork.model.Booking;
import com.example.JavaCourseWork.model.DTO.BookingDTO;
import com.example.JavaCourseWork.model.Flight;
import com.example.JavaCourseWork.model.User;
import com.example.JavaCourseWork.repository.BookingRepository;
import com.example.JavaCourseWork.repository.FlightRepository;
import com.example.JavaCourseWork.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final FlightRepository flightRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository, FlightRepository flightRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.flightRepository = flightRepository;
        this.userRepository = userRepository;
    }
    public BookingDTO addBooking(BookingDTO bookingDTO) {
        Flight flight = flightRepository.findById(bookingDTO.getFlightId()).orElse(null);
        if (flight == null || flight.getAvailableSeats() <= 0) {
            throw new IllegalStateException("No available seats for the selected flight");
        }

        flight.setAvailableSeats(flight.getAvailableSeats() - 1);
        flightRepository.save(flight);

        Booking booking = new Booking(userRepository.findById(getCurrentUserId()).orElse(null), flight);
        booking.setBookingDate(LocalDateTime.now());

        Booking savedBooking = bookingRepository.save(booking);
        return convertToDTO(savedBooking);
    }

    public boolean deleteBooking(Long id) {
        Long currentUserId = getCurrentUserId();

        Optional<Booking> optionalBooking = bookingRepository.findById(id);
        if (optionalBooking.isPresent()) {
            Booking booking = optionalBooking.get();

            if (booking.getUser().getId().equals(currentUserId)) {
                Flight flight = booking.getFlight();
                if (flight != null) {
                    flight.setAvailableSeats(flight.getAvailableSeats() + 1);
                    flightRepository.save(flight);
                }
                bookingRepository.delete(booking);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public List<BookingDTO> getUserBookings() {
        Long userId = getCurrentUserId();
        return bookingRepository.findAllByUserId(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private Long getCurrentUserId() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return user.getId();
    }


    public List<BookingDTO> getAllBookings(String sortBy) {
        List<Booking> bookings;
        switch (sortBy) {
            case "flight":
                bookings = bookingRepository.findAll(Sort.by(Sort.Direction.ASC, "flight"));
                break;
            case "user":
                bookings = bookingRepository.findAll(Sort.by(Sort.Direction.ASC, "user"));
                break;
            case "date":
                bookings = bookingRepository.findAll(Sort.by(Sort.Direction.ASC, "bookingDate"));
                break;
            default:
                throw new IllegalArgumentException("Invalid sorting parameter");
        }
        return bookings.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private BookingDTO convertToDTO(Booking booking) {
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setId(booking.getId());
        bookingDTO.setUserId(booking.getUser().getId());
        bookingDTO.setFlightId(booking.getFlight().getId());
        bookingDTO.setBookingDate(booking.getBookingDate());
        return bookingDTO;
    }

}
