package com.example.arenacinema_springproject.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class CinemaWithCityDTO {
    private int id;
    private String name;
    private String phoneNumber;
    private String address;
    private String email;
    private CityWithoutCinemasDTO cityForCinema;
}
