package com.example.arenacinema_springproject.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TicketAddDTO {

    private int userId;
    private int projectionId;
    private int rownumber;
    private int seatNumber;
}
