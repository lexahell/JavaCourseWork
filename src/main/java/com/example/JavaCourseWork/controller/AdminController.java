package com.example.JavaCourseWork.controller;



import com.example.JavaCourseWork.model.DTO.*;
import com.example.JavaCourseWork.service.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AirlineService airlineService;
    private final AirportService airportService;
    private final FlightService flightService;
    private final BookingService bookingService;
    private final UserService userService;

    @PostMapping("/airlines/add")
    public AirlineDTO addAirline(@RequestBody AirlineDTO airlineDTO) {
        return airlineService.addAirline(airlineDTO);
    }

    @DeleteMapping("/airlines/delete/{id}")
    public ResponseEntity<String> deleteAirline(@PathVariable Long id) {
        boolean isDeleted = airlineService.deleteAirline(id);
        if (isDeleted) {
            return new ResponseEntity<>("Airline with ID " + id + " has been deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Unable to delete airline with ID " + id, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/airlines/id/{id}")
    public AirlineDTO getAirlineById(@PathVariable Long id) {
        return airlineService.getAirlineById(id);
    }

    @GetMapping("/airlines/name/{name}")
    public AirlineDTO getAirlineByName(@PathVariable String name) {
        return airlineService.getAirlineByName(name);
    }

    @GetMapping("/airlines/all")
    public List<AirlineDTO> getAllAirlines() {
        return airlineService.getAllAirlines();
    }



    @PostMapping("/airports/add")
    public AirportDTO addAirport(@RequestBody AirportDTO airportDTO) {
        return airportService.addAirport(airportDTO);
    }

    @DeleteMapping("/airports/delete/{id}")
    public ResponseEntity<String> deleteAirport(@PathVariable Long id) {
        boolean isDeleted = airportService.deleteAirport(id);
        if (isDeleted) {
            return new ResponseEntity<>("Airport with ID " + id + " has been deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Unable to delete airport with ID " + id, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/airports/id/{id}")
    public AirportDTO getAirportById(@PathVariable Long id) {
        return airportService.getAirportById(id);
    }

    @GetMapping("/airports/name/{name}")
    public AirportDTO getAirportByName(@PathVariable String name) {
        return airportService.getAirportByName(name);
    }

    @GetMapping("/airports/all")
    public List<AirportDTO> getAllAirports() {
        return airportService.getAllAirports();
    }




    @PostMapping("/flights/add")
    public FlightDTO addFlight(@RequestBody FlightDTO flightDTO) {
        return flightService.addFlight(flightDTO);
    }

    @DeleteMapping("/flights/delete/{id}")
    public ResponseEntity<String> deleteFlight(@PathVariable Long id) {
        boolean isDeleted = flightService.deleteFlight(id);
        if (isDeleted) {
            return new ResponseEntity<>("Flight with ID " + id + " has been deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Unable to delete flight with ID " + id, HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/flights/id/{id}")
    public FlightDTO getFlightById(@PathVariable Long id) {
        return flightService.getFlightById(id);
    }


    @GetMapping("/bookings/all")
    public List<BookingDTO> getAllBookings(@RequestParam(value = "sortBy", required = false, defaultValue = "date") String sortBy) {
        return bookingService.getAllBookings(sortBy);
    }



    @DeleteMapping("/users/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        boolean deleted = userService.deleteUser(userId);
        if (deleted) {
            return ResponseEntity.ok("User with id " + userId + " deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with id " + userId + " not found.");
        }
    }
    @GetMapping("/users/all")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}