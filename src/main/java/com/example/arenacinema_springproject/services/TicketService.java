package com.example.arenacinema_springproject.services;

import com.example.arenacinema_springproject.exceptions.BadRequestException;
import com.example.arenacinema_springproject.models.dto.TicketAddDTO;
import com.example.arenacinema_springproject.models.entities.Hall;
import com.example.arenacinema_springproject.models.entities.Projection;
import com.example.arenacinema_springproject.models.entities.Ticket;
import com.example.arenacinema_springproject.models.repositories.HallRepository;
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
    private HallRepository hallRepository;




    public Ticket addTicketInService(TicketAddDTO ticket) {
        int userId= ticket.getUserId();
        int projectionId = ticket.getProjectionId();
        int row = ticket.getRownumber();
        int seat = ticket.getSeatNumber();
        if (userId == 0 || projectionId == 0 || row == 0 || seat == 0) {
            throw new BadRequestException("All fields are mandatory");
        }
        if ( row < 0 || seat < 0) {
            throw new BadRequestException("Row and seat numbers should be positive numbers");
        }

        Projection p = projectionRepository.findById(projectionId).orElseThrow(()-> new BadRequestException("No such projection"));
        Hall h = hallRepository.findById(p.getHallForProjection().getId()).orElseThrow(()-> new BadRequestException("No such hall."));
        if (h.getRowsNumber() < row  ) {
            throw new BadRequestException("Invalid row. This hall has " + h.getRowsNumber() + " rows.");
        }
        if (h.getSeatsPerRow() < seat) {
            throw new BadRequestException("Invalid seat number. This hall has " + h.getSeatsPerRow() + " seats per row.");
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
