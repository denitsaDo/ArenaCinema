package com.example.arenacinema_springproject.services;


import com.example.arenacinema_springproject.models.dto.TicketsWithoutUserAndProjectionDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class OccupiedSeatsRowMapper implements RowMapper<TicketsWithoutUserAndProjectionDTO>{

    @Override
    public TicketsWithoutUserAndProjectionDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        TicketsWithoutUserAndProjectionDTO dto = new TicketsWithoutUserAndProjectionDTO();
        dto.setRownumber(rs.getInt("rownumber"));
        dto.setSeatNumber(rs.getInt("seat_number"));
        return dto;
    }
}
