package com.example.arenacinema_springproject.models.dto;

import com.example.arenacinema_springproject.models.entities.Ticket;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TicketResponseDTO {

    private int id;
    private int rownumber;
    private int seatNumber;


}
