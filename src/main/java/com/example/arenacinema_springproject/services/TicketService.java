package com.example.arenacinema_springproject.services;

import com.example.arenacinema_springproject.exceptions.BadRequestException;
import com.example.arenacinema_springproject.models.dto.TicketAddDTO;
import com.example.arenacinema_springproject.models.entities.Ticket;
import com.example.arenacinema_springproject.models.repositories.ProjectionRepository;
import com.example.arenacinema_springproject.models.repositories.TicketRepository;
import com.example.arenacinema_springproject.models.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectionRepository projectionRepository;
    @Autowired
    private ModelMapper modelMapper;

    public TicketAddDTO addTicket(TicketAddDTO ticket) {
        if (ticket.getProjectionId() == 0 || ticket.getUserId() == 0
                || ticket.getRowNumber() == 0 || ticket.getSeatNumber() == 0){
            throw new BadRequestException("All fields are mandatory.");
        }
        //TODO check free seats
        Ticket newTicket = new Ticket();
        newTicket.setUserForTicket(userRepository.findById(ticket.getUserId()).orElseThrow());
        newTicket.setProjectionIdForTicket(projectionRepository.findById(ticket.getProjectionId()).orElseThrow());
        newTicket.setRowNumber(ticket.getRowNumber());
        newTicket.setSeatNumber(ticket.getSeatNumber());
        ticketRepository.save(newTicket);
        return ticket;

    }
}
