package com.example.JavaCourseWork.controller;

import com.example.JavaCourseWork.model.DTO.BookingDTO;
import com.example.JavaCourseWork.service.BookingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final BookingService bookingService;

    @GetMapping("/bookings/all")
    public List<BookingDTO> getAllBookings() {
        return bookingService.getUserBookings();
    }

    @PostMapping("/bookings/add")
    public BookingDTO addFlight(@RequestBody BookingDTO bookingDTO) {
        return bookingService.addBooking(bookingDTO);
    }

    @DeleteMapping("/bookings/delete/{id}")
    public ResponseEntity<String> deleteFlight(@PathVariable Long id) {
        boolean isDeleted = bookingService.deleteBooking(id);
        if (isDeleted) {
            return new ResponseEntity<>("Flight with ID " + id + " has been deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Unable to delete flight with ID " + id, HttpStatus.NOT_FOUND);
        }
    }
}