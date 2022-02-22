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

import java.util.List;

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

    public Ticket addTicketInService(TicketAddDTO ticket) {
        if (ticket.getUserId() == 0) {
            throw new BadRequestException("User id is mandatory");
        }
        if(ticket.getProjectionId() == 0) {
            throw new BadRequestException("Projection id is mandatory");
        }
        if (ticket.getRownumber() == 0) {
            throw new BadRequestException("Row number is mandatory");
        }
        if(ticket.getSeatNumber() == 0){
            throw new BadRequestException("Seat number is mandatory");
        }
        //TODO check free seats
        Ticket newTicket = new Ticket();
        newTicket.setOwner(userRepository.getById(ticket.getUserId()));
        newTicket.setProjectionIdForTicket(projectionRepository.getById(ticket.getProjectionId()));
        newTicket.setRownumber(ticket.getRownumber());
        newTicket.setSeatNumber(ticket.getSeatNumber());
        ticketRepository.save(newTicket);
        return newTicket;

    }

    public List<Ticket> getAllUserTickets(int id) {
        return ticketRepository.findAllByOwnerId(id);
    }
}
