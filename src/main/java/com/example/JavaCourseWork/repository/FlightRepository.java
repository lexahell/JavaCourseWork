package com.example.JavaCourseWork.repository;

import com.example.JavaCourseWork.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {


    Collection<Flight> findAllByOrderByDepartureDateTimeAsc();

    Collection<Flight> findByDepartureAirport_CityAndArrivalAirport_City(String departureCity, String arrivalCity);

    List<Flight> findByDepartureAirport_CityAndArrivalAirport_CityOrderByPrice(String departureCity, String arrivalCity);

    List<Flight> findByDepartureAirport_CityAndArrivalAirport_CityOrderByDepartureDateTime(String departureCity, String arrivalCity);

    List<Flight> findByDepartureAirport_CityAndArrivalAirport_CityOrderByArrivalDateTime(String departureCity, String arrivalCity);
}