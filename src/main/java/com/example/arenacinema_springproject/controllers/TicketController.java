package com.example.arenacinema_springproject.controllers;

import com.example.arenacinema_springproject.models.dto.TicketAddDTO;
import com.example.arenacinema_springproject.models.entities.Ticket;
import com.example.arenacinema_springproject.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TicketController extends BaseController{

    @Autowired
    private TicketService ticketService;

    public Ticket add(TicketAddDTO ticket) {
        Ticket t = ticketService.addTicketInService(ticket);
        return t;
    }
}
