package com.example.JavaCourseWork.service;

import com.example.JavaCourseWork.model.Booking;
import com.example.JavaCourseWork.model.DTO.UserDTO;
import com.example.JavaCourseWork.model.Flight;
import com.example.JavaCourseWork.model.User;
import com.example.JavaCourseWork.repository.BookingRepository;
import com.example.JavaCourseWork.repository.FlightRepository;
import com.example.JavaCourseWork.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final FlightRepository flightRepository;
    private final BookingRepository bookingRepository;
    private final FindByIndexNameSessionRepository<? extends Session> sessionRepository;
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    public boolean create(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            return false;
        }else{
            userRepository.save(user);
            return true;
        }
    }

    public boolean deleteUser(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            List<Booking> userBookings = bookingRepository.findByUserId(userId);
            for (Booking booking : userBookings) {
                Flight flight = booking.getFlight();
                if (flight != null) {
                    flight.setAvailableSeats(flight.getAvailableSeats() + 1);
                    flightRepository.save(flight);
                }
                bookingRepository.delete(booking);
            }

            sessionRepository.deleteById(user.getId().toString());

            userRepository.delete(user);
            return true;
        } else {
            return false;
        }
    }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    public UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        return userDTO;
    }


}
