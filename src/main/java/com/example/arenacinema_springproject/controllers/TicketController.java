package com.example.arenacinema_springproject.controllers;

import com.example.arenacinema_springproject.models.dto.TicketAddDTO;
import com.example.arenacinema_springproject.models.dto.TicketWithMovieInfoDTO;
import com.example.arenacinema_springproject.models.entities.Ticket;
import com.example.arenacinema_springproject.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Stream;

@RestController
public class TicketController extends BaseController{

    @Autowired
    private TicketService ticketService;

    public Ticket add(TicketAddDTO ticket, HttpServletRequest request) {
        Ticket t = ticketService.addTicketInService(ticket, request);
        return t;
    }

    @GetMapping("/tickets")
    public Stream<TicketWithMovieInfoDTO> getUserTickets(HttpServletRequest request){
        validateLogin(request);
        return ticketService.getAllUserTickets(request);
    }
}
