package com.example.arenacinema_springproject.services;


import com.example.arenacinema_springproject.models.dto.OccupiedSeatsDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class OccupiedSeatsRowMapper implements RowMapper<OccupiedSeatsDTO>{

    @Override
    public OccupiedSeatsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        OccupiedSeatsDTO dto = new OccupiedSeatsDTO();
        dto.setRownumber(rs.getInt("rownumber"));
        dto.setSeatNumber(rs.getInt("seat_number"));
        return dto;
    }
}
