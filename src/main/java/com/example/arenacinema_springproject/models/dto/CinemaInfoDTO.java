package com.example.arenacinema_springproject.models.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.tomcat.jni.Local;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


@Getter
@Setter
@NoArgsConstructor
public class CinemaInfoDTO {
    private int cinemaId;
    private String cinemaName;
    private int projectionId;
    private String projectionType;
    private String movieTitle;
    private String hallName;
    private LocalDate date;
    private LocalTime time;
    private int freeSeats;

}
