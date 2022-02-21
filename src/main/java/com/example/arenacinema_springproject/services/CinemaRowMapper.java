package com.example.arenacinema_springproject.services;

import com.example.arenacinema_springproject.models.dto.CinemaInfoDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CinemaRowMapper implements RowMapper<CinemaInfoDTO> {
    @Override
    public CinemaInfoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        CinemaInfoDTO cinema = new CinemaInfoDTO();
        cinema.setCinemaName(rs.getString("cinema_name"));
        cinema.setProjectionId(rs.getInt("projection_id"));
        cinema.setProjectionType(rs.getString("type_name"));
        cinema.setMovieTitle(rs.getString("movie_title"));
        cinema.setHallName(rs.getString("hall_name"));
        cinema.setProjectionStart(rs.getTimestamp("projection_start"));
        return cinema;
    }
}
