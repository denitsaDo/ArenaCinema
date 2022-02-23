package com.example.arenacinema_springproject.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HallAddDTO {

    private String name;
    private int rowsNumber;
    private int seatsPerRow;
    private int cinemaId;

}
