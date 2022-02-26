package com.example.arenacinema_springproject.services;

import com.example.arenacinema_springproject.models.dto.CinemaInfoDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class CinemaRowMapper implements RowMapper<CinemaInfoDTO> {
    @Override
    public CinemaInfoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        CinemaInfoDTO cinema = new CinemaInfoDTO();
        cinema.setCinemaId(rs.getInt("id"));
        cinema.setCinemaName(rs.getString("cinema_name"));
        cinema.setProjectionId(rs.getInt("projection_id"));
        cinema.setProjectionType(rs.getString("type_name"));
        cinema.setMovieTitle(rs.getString("movie_title"));
        cinema.setHallName(rs.getString("hall_name"));
        cinema.setDate(LocalDate.parse(rs.getDate("projection_date").toString()));
        cinema.setTime(LocalTime.parse(rs.getTime("projection_time").toString()));
        cinema.setFreeSeats(rs.getInt("free_seats"));
        return cinema;
    }
}
