package com.example.arenacinema_springproject.services;

import com.example.arenacinema_springproject.exceptions.BadRequestException;
import com.example.arenacinema_springproject.models.dto.TicketsWithoutUserAndProjectionDTO;
import com.example.arenacinema_springproject.models.dto.TicketAddDTO;
import com.example.arenacinema_springproject.models.entities.Hall;
import com.example.arenacinema_springproject.models.entities.Projection;
import com.example.arenacinema_springproject.models.entities.Ticket;
import com.example.arenacinema_springproject.models.repositories.HallRepository;
import com.example.arenacinema_springproject.models.repositories.ProjectionRepository;
import com.example.arenacinema_springproject.models.repositories.TicketRepository;
import com.example.arenacinema_springproject.models.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

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
    @Autowired
    JdbcTemplate jdbcTemplate;



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
        Optional<Ticket> ticketOptional = ticketRepository.findTicketByProjectionIdForTicketAndRownumberAndSeatNumber(projectionRepository.findById(projectionId).orElseThrow(), row, seat);
        if (!ticketOptional.isPresent()){

            Ticket newTicket = new Ticket();
            newTicket.setOwner(userRepository.getById(ticket.getUserId()));
            newTicket.setProjectionIdForTicket(projectionRepository.getById(ticket.getProjectionId()));
            newTicket.setRownumber(ticket.getRownumber());
            newTicket.setSeatNumber(ticket.getSeatNumber());
            ticketRepository.save(newTicket);
            return newTicket;
        }
        else {
            throw new BadRequestException("This place is occupied");
        }

    }

    public List<Ticket> getAllUserTickets(int id) {
        return ticketRepository.findAllByOwnerId(id);
    }

    public Stream<TicketsWithoutUserAndProjectionDTO> getOccupiedSeatsForProjection(int projectionId) {
        String sql = "SELECT rownumber , seat_number FROM tickets WHERE projection_id =" + projectionId;

        return jdbcTemplate.queryForStream(sql,new OccupiedSeatsRowMapper());
    }

    public ArrayList<TicketsWithoutUserAndProjectionDTO> getFreeSeatsForProjection(List<TicketsWithoutUserAndProjectionDTO> taken , int projectionId) throws SQLException {
        Hall hall = projectionRepository.findById(projectionId).orElseThrow().getHallForProjection();

        boolean[][] hallMatrix = new boolean[hall.getRowsNumber()][hall.getSeatsPerRow()];
        for (TicketsWithoutUserAndProjectionDTO ticket : taken) {
            hallMatrix[ticket.getRownumber()-1][ticket.getSeatNumber()-1] = true;
        }
        ArrayList<TicketsWithoutUserAndProjectionDTO> freeTickets = new ArrayList<>();
        for (int row = 0; row < hallMatrix.length; row++) {
            for (int col = 0; col < hallMatrix[0].length; col++) {
                if (!hallMatrix[row][col]){
                    TicketsWithoutUserAndProjectionDTO dto = new TicketsWithoutUserAndProjectionDTO();
                    dto.setRownumber(row);
                    dto.setSeatNumber(col);
                    freeTickets.add(dto);
                }
            }
        }
        ArrayList <TicketsWithoutUserAndProjectionDTO> converted = fixIndexesOfTicketWithoutUserDtoList(freeTickets);
        return converted;
    }

    private ArrayList<TicketsWithoutUserAndProjectionDTO> fixIndexesOfTicketWithoutUserDtoList(ArrayList<TicketsWithoutUserAndProjectionDTO> ticketList){
        for (TicketsWithoutUserAndProjectionDTO ticket :
                ticketList) {
            ticket.setRownumber(ticket.getRownumber()+1);
            ticket.setSeatNumber(ticket.getSeatNumber()+1);
        }
        return ticketList;
    }
}
