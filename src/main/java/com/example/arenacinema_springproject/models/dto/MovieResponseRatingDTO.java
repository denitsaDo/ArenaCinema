package com.example.arenacinema_springproject.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor

public class MovieResponseRatingDTO {

    private int rating;
}
