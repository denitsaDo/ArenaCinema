package com.example.arenacinema_springproject.controllers;

import com.example.arenacinema_springproject.models.dto.TicketResponseDTO;
import com.example.arenacinema_springproject.models.dto.TicketWithMovieInfoDTO;
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
import java.util.ArrayList;
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
    public Stream<TicketWithMovieInfoDTO> getUserTickets(HttpServletRequest request){
        validateLogin(request);
        return ticketService.getAllUserTickets(request);
    }
}
