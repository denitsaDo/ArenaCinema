package com.example.arenacinema_springproject.controllers;


import com.example.arenacinema_springproject.models.dto.CinemaAddDTO;
import com.example.arenacinema_springproject.models.entities.Cinema;
import com.example.arenacinema_springproject.services.CinemaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class CinemaController extends BaseController{

    @Autowired
    private CinemaService cinemaService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/cinemas")
    public ResponseEntity<Cinema> add(@RequestBody CinemaAddDTO cinema, HttpServletRequest request) {
        validateLogin(request);
        adminLogin(request);
        Cinema c = cinemaService.add(cinema);
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
    public ResponseEntity<Cinema> edit(@RequestBody Cinema cinema, HttpServletRequest request) {
        validateLogin(request);
        adminLogin(request);
        Cinema c = cinemaService.edit(cinema);
        return ResponseEntity.ok(c);
    }

    @GetMapping("/cinemas/{id}")
    public ResponseEntity <Cinema> getById(@PathVariable int id){
        Cinema c = cinemaService.getCinemaById(id);
        return ResponseEntity.ok(c);
    }


//    @GetMapping("/cinemas")
//    public List getAll(@PathVariable int cityId){
//        return cinemaService.getAllByCityId(cityId);
//    }

}
