package com.example.arenacinema_springproject.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
public class TicketWithMovieInfoDTO {

    private String movieTitle;
    private LocalDate date;
    private LocalTime time;
    private int rownumber;
    private int seatNumber;
}
