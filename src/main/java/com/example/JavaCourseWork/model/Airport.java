package com.example.JavaCourseWork.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "airports")
public class Airport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "airport_code")
    private String airportCode;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    public Airport(String name, String airportCode, String city, String country) {
        this.name = name;
        this.airportCode = airportCode;
        this.city = city;
        this.country = country;
    }
}
