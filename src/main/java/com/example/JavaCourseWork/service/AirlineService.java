package com.example.JavaCourseWork.service;

import com.example.JavaCourseWork.model.Airline;
import com.example.JavaCourseWork.model.DTO.AirlineDTO;
import com.example.JavaCourseWork.repository.AirlineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AirlineService {
    private final AirlineRepository airlineRepository;

    @Autowired
    public AirlineService(AirlineRepository airlineRepository) {
        this.airlineRepository = airlineRepository;
    }

    public AirlineDTO addAirline(AirlineDTO airlineDTO) {
        Airline airline = new Airline(airlineDTO.getName());
        airline = airlineRepository.save(airline);
        return convertToDto(airline);
    }

    public boolean deleteAirline(Long id) {
        Optional<Airline> optionalAirline = airlineRepository.findById(id);
        if (optionalAirline.isPresent()) {
            airlineRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public AirlineDTO getAirlineById(Long id) {
        Airline airline = airlineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Airline not found with id: " + id));
        return convertToDto(airline);
    }

    public AirlineDTO getAirlineByName(String name) {
        Airline airline = airlineRepository.findByName(name);
        if (airline == null) {
            throw new RuntimeException("Airline not found with name: " + name);
        }
        return convertToDto(airline);
    }

    public List<AirlineDTO> getAllAirlines() {
        List<Airline> airlines = airlineRepository.findAll();
        return airlines.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private AirlineDTO convertToDto(Airline airline) {
        AirlineDTO airlineDTO = new AirlineDTO();
        airlineDTO.setId(airline.getId());
        airlineDTO.setName(airline.getName());
        return airlineDTO;
    }
}
