package com.example.arenacinema_springproject.controllers;

import com.example.arenacinema_springproject.models.dto.HallAddDTO;
import com.example.arenacinema_springproject.models.entities.Hall;
import com.example.arenacinema_springproject.services.HallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class HallController extends BaseController{

    @Autowired
    private HallService hallService;

    @PostMapping("/halls")
    public ResponseEntity<Hall> add(@RequestBody HallAddDTO hall, HttpServletRequest request) {
        validateLogin(request);
        adminLogin(request);
        Hall h = hallService.addHall(hall);
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
    public ResponseEntity<Hall> edit(@RequestBody Hall hall, HttpServletRequest request) {
        validateLogin(request);
        adminLogin(request);
        Hall h = hallService.edit(hall);
        return ResponseEntity.ok(h);
    }

    @GetMapping("/halls/{id}")
    public ResponseEntity <Hall> getById(@PathVariable int id){
        Hall h = hallService.getHallById(id);
        return ResponseEntity.ok(h);
    }

    @GetMapping("/halls")
    public List getAll(HttpServletRequest request){
        validateLogin(request);
        return hallService.getAllHalls();
    }
}
