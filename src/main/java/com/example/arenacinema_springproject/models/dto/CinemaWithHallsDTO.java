package com.example.arenacinema_springproject.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class CinemaWithHallsDTO {
    private int id;
    private String name;
    List<HallWithoutCinemaDTO> halls;

}
