package com.example.arenacinema_springproject.controllers;

import com.example.arenacinema_springproject.models.dto.HallAddDTO;
import com.example.arenacinema_springproject.models.dto.HallEditDTO;
import com.example.arenacinema_springproject.models.dto.HallWithCinemaDTO;
import com.example.arenacinema_springproject.services.HallService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class HallController extends BaseController{

    @Autowired
    private HallService hallService;

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
        hallService.delete(id);

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
        HallWithCinemaDTO h = hallService.getHallById(id);
        return ResponseEntity.ok(h);
    }
}
