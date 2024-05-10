package com.example.JavaCourseWork.repository;


import com.example.JavaCourseWork.model.Booking;
import com.example.JavaCourseWork.model.Flight;
import com.example.JavaCourseWork.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {


    List<Booking> findAllByUserId(Long userId);


    List<Booking> findByUserId(Long userId);
}
