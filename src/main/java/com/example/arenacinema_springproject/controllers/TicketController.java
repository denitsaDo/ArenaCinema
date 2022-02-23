package com.example.arenacinema_springproject.controllers;

import com.example.arenacinema_springproject.models.dto.OccupiedSeatsDTO;
import com.example.arenacinema_springproject.models.dto.TicketAddDTO;
import com.example.arenacinema_springproject.models.entities.Ticket;
import com.example.arenacinema_springproject.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class TicketController extends BaseController{

    @Autowired
    private TicketService ticketService;

    public Ticket add(TicketAddDTO ticket) {
        Ticket t = ticketService.addTicketInService(ticket);
        return t;
    }

    @GetMapping("/tickets/{id}")
    public List<Ticket> getUserTickets(@PathVariable int id, HttpServletRequest request){
        validateLogin(request);
        return ticketService.getAllUserTickets(id);
    }

    @GetMapping("/tickets/occupied/{projectionId}")
    public List<OccupiedSeatsDTO> getAll(@PathVariable int projectionId, HttpServletRequest request) {
        validateLogin(request);
        Stream<OccupiedSeatsDTO> result = ticketService.getOccupiedSeatsForProjection(projectionId);
        List<OccupiedSeatsDTO> occupiedSeats = result.collect(Collectors.toList());
        return occupiedSeats;
    }
}
