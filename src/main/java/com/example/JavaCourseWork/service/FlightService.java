package com.example.JavaCourseWork.service;

import com.example.JavaCourseWork.model.Airline;
import com.example.JavaCourseWork.model.Airport;
import com.example.JavaCourseWork.model.DTO.FlightDTO;
import com.example.JavaCourseWork.model.Flight;
import com.example.JavaCourseWork.repository.AirlineRepository;
import com.example.JavaCourseWork.repository.AirportRepository;
import com.example.JavaCourseWork.repository.FlightRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class FlightService {

    private FlightRepository flightRepository;
    private AirlineRepository airlineRepository;
    private AirportRepository airportRepository;

    @Autowired
    public FlightService(FlightRepository flightRepository, AirportRepository airportRepository, AirlineRepository airlineRepository) {
        this.flightRepository = flightRepository;
        this.airlineRepository = airlineRepository;
        this.airportRepository = airportRepository;
    }

    public FlightDTO addFlight(FlightDTO flightDTO) {
        Airline airline = airlineRepository.findById(flightDTO.getAirlineId())
                .orElseThrow(() -> new EntityNotFoundException("Airline not found with id: " + flightDTO.getAirlineId()));

        Airport departureAirport = airportRepository.findById(flightDTO.getDepartureAirportId())
                .orElseThrow(() -> new EntityNotFoundException("Departure airport not found with id: " + flightDTO.getDepartureAirportId()));

        Airport arrivalAirport = airportRepository.findById(flightDTO.getArrivalAirportId())
                .orElseThrow(() -> new EntityNotFoundException("Arrival airport not found with id: " + flightDTO.getArrivalAirportId()));

        Flight flight = new Flight(
                flightDTO.getFlightNumber(),
                airline,
                departureAirport,
                arrivalAirport,
                flightDTO.getDepartureDateTime(),
                flightDTO.getArrivalDateTime(),
                flightDTO.getPrice(),
                flightDTO.getAvailableSeats()
        );

        flight = flightRepository.save(flight);
        return convertToDTO(flight);
    }
    public boolean deleteFlight(Long id) {
        if (flightRepository.existsById(id)) {
            flightRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public FlightDTO getFlightById(Long id) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found with id: " + id));
        return convertToDTO(flight);
    }


    public List<FlightDTO> getAllFlights() {
        return flightRepository.findAllByOrderByDepartureDateTimeAsc()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }



    public List<FlightDTO> searchFlights(String departureCity, String arrivalCity, String sortBy) {
        if (sortBy != null) {
            switch (sortBy) {
                case "price":
                    return flightRepository.findByDepartureAirport_CityAndArrivalAirport_CityOrderByPrice(departureCity, arrivalCity).stream()
                            .map(this::convertToDTO)
                            .collect(Collectors.toList());
                case "departureDateTime":
                    return flightRepository.findByDepartureAirport_CityAndArrivalAirport_CityOrderByDepartureDateTime(departureCity, arrivalCity).stream()
                            .map(this::convertToDTO)
                            .collect(Collectors.toList());
                case "arrivalDateTime":
                    return flightRepository.findByDepartureAirport_CityAndArrivalAirport_CityOrderByArrivalDateTime(departureCity, arrivalCity).stream()
                            .map(this::convertToDTO)
                            .collect(Collectors.toList());
                default:
                    throw new IllegalArgumentException("Invalid sorting parameter");
            }
        } else {
            return flightRepository.findByDepartureAirport_CityAndArrivalAirport_City(departureCity, arrivalCity).stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }
    }

    private FlightDTO convertToDTO(Flight flight) {
        FlightDTO flightDTO = new FlightDTO();
        flightDTO.setId(flight.getId());
        flightDTO.setFlightNumber(flight.getFlightNumber());
        flightDTO.setAirlineId(flight.getAirline().getId());
        flightDTO.setDepartureAirportId(flight.getDepartureAirport().getId());
        flightDTO.setArrivalAirportId(flight.getArrivalAirport().getId());
        flightDTO.setDepartureDateTime(flight.getDepartureDateTime());
        flightDTO.setArrivalDateTime(flight.getArrivalDateTime());
        flightDTO.setPrice(flight.getPrice());
        flightDTO.setAvailableSeats(flight.getAvailableSeats());
        return flightDTO;
    }
}
