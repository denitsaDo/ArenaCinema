package com.example.arenacinema_springproject.services;

import com.example.arenacinema_springproject.configuration.JdbcConfiguration;
import com.example.arenacinema_springproject.exceptions.BadRequestException;
import com.example.arenacinema_springproject.exceptions.NoContentException;
import com.example.arenacinema_springproject.exceptions.NotFoundException;
import com.example.arenacinema_springproject.models.dto.*;
import com.example.arenacinema_springproject.models.entities.Cinema;
import com.example.arenacinema_springproject.models.repositories.CinemaRepository;
import com.example.arenacinema_springproject.models.repositories.CityRepository;
import com.example.arenacinema_springproject.models.repositories.ProjectionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CinemaService {

    @Autowired
    private CinemaRepository cinemaRepository;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    ProjectionRepository projectionRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    JdbcConfiguration jdbcConfiguration;


    public CinemaWithCityAndHallsDTO add(CinemaAddDTO cinema) {
        if (cinema.getName() == null || cinema.getName().isBlank()) {
            throw new BadRequestException("Cinema name is mandatory!");
        }
        if (cinemaRepository.findFirstByName(cinema.getName())!= null) {
            throw new BadRequestException("Cinema already exists!");
        }
        if (cinema.getCityId() == 0 ) {
            throw new BadRequestException("City is mandatory!");
        }
        if (cinema.getPhoneNumber() == null || cinema.getPhoneNumber().isBlank()) {
            throw new BadRequestException("Phone number is mandatory!");
        }
        if (!cinema.getPhoneNumber().matches("[0-9]{10}")) {
            throw new BadRequestException("Invalid phone number!");
        }
        if (cinema.getAddress() == null || cinema.getAddress().isBlank()) {
            throw new BadRequestException("Address is mandatory!");
        }
        if (cinema.getEmail() == null || cinema.getEmail().isBlank()) {
            throw new BadRequestException("Email is mandatory!");
        }

        Cinema c = new Cinema();
        c.setName(cinema.getName());
        c.setCitySelected(cityRepository.findById(cinema.getCityId()).orElseThrow(() -> new NotFoundException("City not found")));
        c.setPhoneNumber(cinema.getPhoneNumber());
        c.setAddress(cinema.getAddress());
        c.setEmail(cinema.getEmail());
        cinemaRepository.save(c);
        CinemaWithCityAndHallsDTO dto = new CinemaWithCityAndHallsDTO();
        modelMapper.map(c,dto);
        dto.setCityForCinema(modelMapper.map(c.getCitySelected(), CityWithoutCinemasDTO.class));
        return dto;
    }

    public CinemaWithCityAndHallsDTO edit(CinemaEditDTO cinema) {
        Optional<Cinema> opt = cinemaRepository.findById(cinema.getId());
        if(opt.isPresent()){

            Cinema c = modelMapper.map(cinema, Cinema.class);
            c.setCitySelected(cityRepository.findById(cinema.getCityId()).orElseThrow(()-> new BadRequestException("No such city.")));
            cinemaRepository.save(c);
            CinemaWithCityAndHallsDTO dto = new CinemaWithCityAndHallsDTO();
            modelMapper.map(c,dto);
            dto.setCityForCinema(modelMapper.map(c.getCitySelected(), CityWithoutCinemasDTO.class));
            return dto;
        }
        else{
            throw new NotFoundException("Cinema not found.");
        }
    }

    public Cinema getCinemaById(int id) {
        return  cinemaRepository.findById(id).orElseThrow(()-> new NotFoundException("Cinema not found"));
    }

    public void delete(Cinema cinema) {
        Optional<Cinema> optional = cinemaRepository.findById(cinema.getId());
        if (optional.isPresent() ) {
            cinemaRepository.delete(cinema);
            throw new NoContentException();
        }
        else {
            throw new NotFoundException("No such cinema.");
        }
    }

    public CinemaWithCityAndHallsDTO getCinemaWithCityAndHallsDById(int id) {
        Cinema c = getCinemaById(id);
        CinemaWithCityAndHallsDTO dto = new CinemaWithCityAndHallsDTO();
        dto.setId(c.getId());
        dto.setName(c.getName());
        dto.setPhoneNumber(c.getPhoneNumber());
        dto.setAddress(c.getAddress());
        dto.setEmail(c.getEmail());
        dto.setCityForCinema(modelMapper.map(c.getCitySelected(), CityWithoutCinemasDTO.class));
        dto.setHalls(c.getHalls().stream().map(hall -> modelMapper.map(hall, HallWithoutCinemaDTO.class)).collect(Collectors.toList()));
        return dto;
    }

    public Stream<CinemaInfoDTO> getCinemasWithFilter(CinemaWithFiltersDTO cinemaWithFilters) {
        String sql = "SELECT c.id AS id, c.name AS cinema_name, p.id AS projection_id, m.title AS movie_title, h.name AS hall_name, \n" +
                "t.name AS type_name, p.start_time AS projection_start, \n" +
                "((h.rows_number * h.seats_per_row) - (SELECT count(*) FROM kinoarena.tickets where tickets.projection_id = p.id))  AS free_seats\n" +
                "FROM cinemas AS c\n" +
                "LEFT JOIN halls AS h ON( c.id = h.cinema_id)\n" +
                "LEFT JOIN projections AS p ON(h.id = p.hall_id)\n" +
                "LEFT JOIN movies AS m ON(m.id = p.movie_id)\n" +
                "LEFT JOIN cities ON(cities.id = c.city_id)\n" +
                "LEFT JOIN types AS t ON(p.type_id = t.id)";

        if (cinemaWithFilters.getCity() != null && cinemaWithFilters.getType() != null) {
            sql+= "WHERE cities.name LIKE " + "'%" + cinemaWithFilters.getCity() + "%'" + "AND t.name LIKE " + "'%" + cinemaWithFilters.getType() + "%'";
        }
        else {
            if (cinemaWithFilters.getCity() != null ) {
                sql+= "WHERE cities.name LIKE " + "'%" + cinemaWithFilters.getCity() + "%'" ;
            }

            if ((cinemaWithFilters.getType() != null) ) {
                sql+= "WHERE t.name LIKE " + "'%" + cinemaWithFilters.getType() + "%'";
            }
        }

        return jdbcTemplate.queryForStream(sql, new CinemaRowMapper());
    }
}
