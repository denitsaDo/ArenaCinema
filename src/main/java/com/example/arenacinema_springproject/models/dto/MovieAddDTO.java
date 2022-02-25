package com.example.arenacinema_springproject.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
public class MovieAddDTO {

    private String title;
    private int duration;
    private String description;
    private String actors;
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private LocalDate premiere;
    private String director;
    private int categoryId;
}
