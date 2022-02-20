package com.example.arenacinema_springproject.controllers;


import com.example.arenacinema_springproject.exceptions.BadRequestException;
import com.example.arenacinema_springproject.exceptions.NotFoundException;
import com.example.arenacinema_springproject.models.dto.*;
import com.example.arenacinema_springproject.models.entities.Cinema;
import com.example.arenacinema_springproject.models.entities.City;
import com.example.arenacinema_springproject.models.repositories.CinemaRepository;
import com.example.arenacinema_springproject.services.CinemaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class CinemaController extends BaseController{

    @Autowired
    private CinemaService cinemaService;
    @Autowired
    private CinemaRepository cinemaRepository;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/cinemas")
    public ResponseEntity<CinemaWithCityDTO> add(@RequestBody CinemaAddDTO cinema, HttpServletRequest request) {
        validateLogin(request);
        adminLogin(request);
        CinemaWithCityDTO c = cinemaService.add(cinema);

        return ResponseEntity.ok(c);
    }

    @DeleteMapping("/cinemas/{id}")
    public void delete(@PathVariable int id, HttpServletRequest request) {
        validateLogin(request);
        adminLogin(request);
        Cinema c = cinemaService.getCinemaById(id);
        cinemaService.delete(c);

    }

    @PutMapping("/cinemas")
    public ResponseEntity<CinemaWithCityDTO> edit(@RequestBody CinemaEditDTO cinema, HttpServletRequest request) {
        validateLogin(request);
        adminLogin(request);
        CinemaWithCityDTO c = cinemaService.edit(cinema);
        return ResponseEntity.ok(c);
    }

    @GetMapping("/cinemas/{id}")
    public ResponseEntity <CinemaWithCityDTO> getById(@PathVariable int id){
        Cinema c = cinemaService.getCinemaById(id);
        CinemaWithCityDTO dto = new CinemaWithCityDTO();
        dto.setId(c.getId());
        dto.setName(c.getName());
        dto.setPhoneNumber(c.getPhoneNumber());
        dto.setAddress(c.getAddress());
        dto.setEmail(c.getEmail());
        dto.setCityForCinema(modelMapper.map(c.getCitySelected(), CityWithoutCinemasDTO.class));
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/cinemas")
    public List<CinemaResponseDTO> getAllCinemasByCityName (@RequestBody City city) {
        Optional<List<Cinema>> opt = cinemaRepository.findAllByCitySelected_Name(city.getName());
        if (opt.isPresent()) {
            if (opt.get().size() == 0) {
                throw new BadRequestException("No cinemas in this city");
            } else
                return opt.get().stream().map(cinema -> modelMapper.map(cinema, CinemaResponseDTO.class)).collect(Collectors.toList());

        }
        else throw new BadRequestException("No cinemas");
    }
}
