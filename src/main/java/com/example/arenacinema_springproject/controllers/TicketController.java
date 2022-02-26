package com.example.arenacinema_springproject.controllers;

import com.example.arenacinema_springproject.models.dto.TicketResponseDTO;
import com.example.arenacinema_springproject.models.dto.TicketsWithoutUserAndProjectionDTO;
import com.example.arenacinema_springproject.models.dto.TicketAddDTO;
import com.example.arenacinema_springproject.models.entities.Ticket;
import com.example.arenacinema_springproject.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class TicketController extends BaseController{

    @Autowired
    private TicketService ticketService;

    public Ticket add(TicketAddDTO ticket, int userId) {
        Ticket t = ticketService.addTicketInService(ticket, userId);
        return t;
    }

    @GetMapping("/tickets")
    public List<TicketResponseDTO> getUserTickets(HttpServletRequest request){
        validateLogin(request);
        int userId = (Integer) request.getSession().getAttribute(USER_ID);
        return ticketService.getAllUserTickets(userId);
    }

    @GetMapping("/tickets/occupied/{projectionId}")
    public List<TicketsWithoutUserAndProjectionDTO> getAllSoldTickets(@PathVariable int projectionId, HttpServletRequest request) {
        validateLogin(request);
        Stream<TicketsWithoutUserAndProjectionDTO> result = ticketService.getOccupiedSeatsForProjection(projectionId);
        List<TicketsWithoutUserAndProjectionDTO> occupiedSeats = result.collect(Collectors.toList());
        return occupiedSeats;
    }

    @GetMapping("/tickets/free/{projectionId}")
    public List<TicketsWithoutUserAndProjectionDTO> getFreeTicketsForProjection( @PathVariable int projectionId, HttpServletRequest request) throws SQLException {
        validateLogin(request);
        List<TicketsWithoutUserAndProjectionDTO> reservedTickets = ticketService.getOccupiedSeatsForProjection(projectionId).collect(Collectors.toList());
        List<TicketsWithoutUserAndProjectionDTO> ticketList = ticketService.getFreeSeatsForProjection(reservedTickets,projectionId);
        return ticketList;
    }
}
