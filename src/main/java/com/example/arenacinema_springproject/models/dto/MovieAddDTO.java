package com.example.arenacinema_springproject.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
public class MovieAddDTO {

    private String title;
    private int duration;
    private String description;
    private String actors;
    private LocalDate premiere;
    private String director;
    private int categoryId;
    private String poster_url;
}
