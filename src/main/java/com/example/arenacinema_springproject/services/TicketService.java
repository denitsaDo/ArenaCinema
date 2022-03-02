package com.example.arenacinema_springproject.services;

import com.example.arenacinema_springproject.exceptions.BadRequestException;
import com.example.arenacinema_springproject.models.dto.TicketWithMovieInfoDTO;
import com.example.arenacinema_springproject.models.dto.TicketsWithoutUserAndProjectionDTO;
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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.example.arenacinema_springproject.controllers.BaseController.USER_ID;

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



    public Ticket addTicketInService(TicketAddDTO ticket, HttpServletRequest request) {
        int userId = (Integer) request.getSession().getAttribute(USER_ID);
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
            newTicket.setOwner(userRepository.getById(userId));
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

    public Stream<TicketWithMovieInfoDTO> getAllUserTickets (HttpServletRequest request) {
        int userId = (Integer) request.getSession().getAttribute(USER_ID);
        String sql = "SELECT m.title AS title, p.start_time AS projection_date, p.start_time AS projection_time, \n" +
                "t.rownumber AS rownumber, t.seat_number AS seat_number\n" +
                "FROM tickets AS t \n" +
                "JOIN projections AS p ON (t.projection_id = p.id)\n" +
                "JOIN movies AS m ON (p.movie_id = m.id)\n" +
                "WHERE t.user_id = " + userId;
            return jdbcTemplate.queryForStream(sql, new TicketRowMapper());
    }

    public ArrayList<TicketsWithoutUserAndProjectionDTO> getSeatsForProjection(int projectionId) {
        Hall hall = projectionRepository.findById(projectionId).orElseThrow().getHallForProjection();
        TicketsWithoutUserAndProjectionDTO [][] hallMatrix = new TicketsWithoutUserAndProjectionDTO [hall.getRowsNumber()][hall.getSeatsPerRow()];
        for (int row = 0; row < hallMatrix.length; row++) {
            for (int col = 0; col < hallMatrix[0].length; col++) {
                hallMatrix[row][col] = new TicketsWithoutUserAndProjectionDTO();
            }
        }
        List<TicketsWithoutUserAndProjectionDTO> taken = getOccupiedSeatsForProjection(projectionId).collect(Collectors.toList());
        for (TicketsWithoutUserAndProjectionDTO ticket : taken) {
            hallMatrix[ticket.getRownumber()-1][ticket.getSeatNumber()-1].setTaken(true);
        }
        ArrayList<TicketsWithoutUserAndProjectionDTO> allSeats = new ArrayList<>();
        for (int row = 0; row < hallMatrix.length; row++) {
            for (int col = 0; col < hallMatrix[0].length; col++) {
                    TicketsWithoutUserAndProjectionDTO dto = hallMatrix[row][col];
                    dto.setRownumber(row);
                    dto.setSeatNumber(col);
                    allSeats.add(dto);
            }
        }
        ArrayList <TicketsWithoutUserAndProjectionDTO> converted = fixIndexesOfTicketWithoutUserDtoList(allSeats);
        return converted;
    }


    private Stream<TicketsWithoutUserAndProjectionDTO> getOccupiedSeatsForProjection(int projectionId) {
        String sql = "SELECT rownumber , seat_number FROM tickets WHERE projection_id = ?" ;

        return jdbcTemplate.queryForStream(sql,new OccupiedSeatsRowMapper(), projectionId);
    }

    private ArrayList<TicketsWithoutUserAndProjectionDTO> fixIndexesOfTicketWithoutUserDtoList(ArrayList<TicketsWithoutUserAndProjectionDTO> ticketList){
        for (TicketsWithoutUserAndProjectionDTO ticket :
                ticketList) {
            ticket.setRownumber(ticket.getRownumber()+1);
            ticket.setSeatNumber(ticket.getSeatNumber()+1);
        }
        return ticketList;
    }

    private class OccupiedSeatsRowMapper implements RowMapper<TicketsWithoutUserAndProjectionDTO>{

        @Override
        public TicketsWithoutUserAndProjectionDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            TicketsWithoutUserAndProjectionDTO dto = new TicketsWithoutUserAndProjectionDTO();
            dto.setRownumber(rs.getInt("rownumber"));
            dto.setSeatNumber(rs.getInt("seat_number"));
            return dto;
        }
    }

    private class TicketRowMapper implements RowMapper<TicketWithMovieInfoDTO>{
        @Override
        public TicketWithMovieInfoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            TicketWithMovieInfoDTO dto = new TicketWithMovieInfoDTO();
            dto.setMovieTitle(rs.getString("title"));
            dto.setDate(LocalDate.parse(rs.getDate("projection_date").toString()));
            dto.setTime(LocalTime.parse(rs.getTime("projection_time").toString()));
            dto.setRownumber(rs.getInt("rownumber"));
            dto.setSeatNumber(rs.getInt("seat_number"));
            return dto;
        }
    }
}
