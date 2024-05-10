package com.example.JavaCourseWork.service;


import com.example.JavaCourseWork.model.Airport;
import com.example.JavaCourseWork.model.DTO.AirportDTO;
import com.example.JavaCourseWork.repository.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AirportService {
    private final AirportRepository airportRepository;

    @Autowired
    public AirportService(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    public AirportDTO addAirport(AirportDTO airportDTO) {
        Airport airport = new Airport(
                airportDTO.getName(),
                airportDTO.getAirportCode(),
                airportDTO.getCity(),
                airportDTO.getCountry()
        );
        airport = airportRepository.save(airport);
        return convertToDto(airport);
    }

    public boolean deleteAirport(Long id) {
        Optional<Airport> optionalAirport = airportRepository.findById(id);
        if (optionalAirport.isPresent()) {
            airportRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }


    public AirportDTO getAirportById(Long id) {
        Airport airport = airportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Airport not found with id: " + id));
        return convertToDto(airport);
    }

    public AirportDTO getAirportByName(String name) {
        Airport airport = airportRepository.findByName(name);
        if (airport == null) {
            throw new RuntimeException("Airport not found with name: " + name);
        }
        return convertToDto(airport);
    }

    public List<AirportDTO> getAllAirports() {
        List<Airport> airports = airportRepository.findAll();
        return airports.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private AirportDTO convertToDto(Airport airport) {
        AirportDTO airportDTO = new AirportDTO();
        airportDTO.setId(airport.getId());
        airportDTO.setName(airport.getName());
        airportDTO.setAirportCode(airport.getAirportCode());
        airportDTO.setCity(airport.getCity());
        airportDTO.setCountry(airport.getCountry());
        return airportDTO;
    }
}
