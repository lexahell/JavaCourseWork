package com.example.JavaCourseWork.controller;


import com.example.JavaCourseWork.model.DTO.FlightDTO;
import com.example.JavaCourseWork.service.FlightService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/flights")
public class FlightController {
    private final FlightService flightService;

    @GetMapping("/all")
    public List<FlightDTO> getAllFlights() {
        return flightService.getAllFlights();
    }

    @GetMapping("/search")
    public List<FlightDTO> searchFlights(
            @RequestParam("departureCity") String departureCity,
            @RequestParam("arrivalCity") String arrivalCity,
            @RequestParam(value = "sortBy", required = false) String sortBy) {
        return flightService.searchFlights(departureCity, arrivalCity, sortBy);
    }

}
