package com.example.arenacinema_springproject.models.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Timestamp;


@Getter
@Setter
@NoArgsConstructor
public class CinemaInfoDTO {
    private String cinemaName;
    private int projectionId;
    private String projectionType;
    private String movieTitle;
    private String hallName;
    private Timestamp projectionStart;

}
