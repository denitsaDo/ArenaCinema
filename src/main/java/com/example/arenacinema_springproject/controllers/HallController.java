package com.example.arenacinema_springproject.controllers;

import com.example.arenacinema_springproject.exceptions.BadRequestException;
import com.example.arenacinema_springproject.models.dto.*;
import com.example.arenacinema_springproject.models.entities.Cinema;
import com.example.arenacinema_springproject.models.entities.City;
import com.example.arenacinema_springproject.models.entities.Hall;
import com.example.arenacinema_springproject.services.HallService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class HallController extends BaseController{

    @Autowired
    private HallService hallService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/halls")
    public ResponseEntity<HallWithCinemaDTO> add(@RequestBody HallAddDTO hall, HttpServletRequest request) {
        validateLogin(request);
        adminLogin(request);
        HallWithCinemaDTO h = hallService.addHall(hall);
        return ResponseEntity.ok(h);
    }


    @DeleteMapping("/halls/{id}")
    public void delete(@PathVariable int id, HttpServletRequest request) {
        validateLogin(request);
        adminLogin(request);
        Hall h = hallService.getHallById(id);
        hallService.delete(h);

    }


    @PutMapping("/halls")
    public ResponseEntity<HallWithCinemaDTO> edit(@RequestBody HallEditDTO hall, HttpServletRequest request) {
        validateLogin(request);
        adminLogin(request);
        HallWithCinemaDTO h = hallService.edit(hall);
        return ResponseEntity.ok(h);
    }

    @GetMapping("/halls/{id}")
    public ResponseEntity <HallWithCinemaDTO> getById(@PathVariable int id){
        Hall h = hallService.getHallById(id);
        HallWithCinemaDTO dto = new HallWithCinemaDTO();
        dto.setId(h.getId());
        dto.setName(h.getName());
        dto.setRowsNumber(h.getRowsNumber());
        dto.setSeatsPerRow(h.getSeatsPerRow());
        dto.setCinemaForThisHall(modelMapper.map(h.getCinemaIn(), CinemaWithoutHallDTO.class));
        return ResponseEntity.ok(dto);
    }


}
