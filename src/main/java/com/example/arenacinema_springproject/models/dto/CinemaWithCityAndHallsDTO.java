package com.example.arenacinema_springproject.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class CinemaWithCityAndHallsDTO {
    private int id;
    private String name;
    private String phoneNumber;
    private String address;
    private String email;
    private CityWithoutCinemasDTO cityForCinema;
    List<HallWithoutCinemaDTO> halls;
}
