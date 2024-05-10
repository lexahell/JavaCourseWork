package com.example.JavaCourseWork.model.DTO;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AirportDTO {
    private Long id;
    private String name;
    private String airportCode;
    private String city;
    private String country;
}
