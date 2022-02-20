package com.example.arenacinema_springproject.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class HallWithCinemaDTO {
    private int id;
    private String name;
    private int capacity;
    private CinemaWithoutHallDTO cinemaForThisHall;
}
