package com.example.arenacinema_springproject.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
public class ProjectionEditDTO {
    private int id;
    private int movieId;
    private int hallId;
    private int typeId;
    private LocalDateTime startTime;

}
