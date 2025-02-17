package com.example.JavaCourseWork.repository;

import com.example.JavaCourseWork.model.Airline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AirlineRepository extends JpaRepository<Airline, Long> {
    Airline findByName(String name);
}
