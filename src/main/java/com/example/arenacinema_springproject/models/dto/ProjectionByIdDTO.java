package com.example.arenacinema_springproject.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
public class ProjectionByIdDTO {

    private String movieName;
    private LocalDateTime startTime;
    private ArrayList<TicketsWithoutUserAndProjectionDTO> seats;
}
