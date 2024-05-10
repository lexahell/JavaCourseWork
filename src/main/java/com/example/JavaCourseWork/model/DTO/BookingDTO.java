package com.example.JavaCourseWork.model.DTO;

import java.time.LocalDateTime;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookingDTO {
    private Long id;
    private Long userId;
    private Long flightId;
    private LocalDateTime bookingDate;
}
