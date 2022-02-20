package com.example.arenacinema_springproject.controllers;

import com.example.arenacinema_springproject.models.dto.CityAddDto;
import com.example.arenacinema_springproject.models.dto.CityWithCinemasDTO;
import com.example.arenacinema_springproject.models.entities.City;
import com.example.arenacinema_springproject.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class CityController extends BaseController{


    @Autowired
    private CityService cityService;

    @PostMapping("/cities")
    public ResponseEntity<City> add(@RequestBody CityAddDto city, HttpServletRequest request) {
        validateLogin(request);
        adminLogin(request);
        String name = city.getName();
        City c = cityService.add(name);
        return ResponseEntity.ok(c);
    }

    @DeleteMapping("/cities/{id}")
    public void delete(@PathVariable int id, HttpServletRequest request) {
        validateLogin(request);
        adminLogin(request);
        City c = cityService.getById(id);
        cityService.delete(c);
    }

    @PutMapping("/cities")
    public ResponseEntity<City> edit(@RequestBody City city, HttpServletRequest request) {
        validateLogin(request);
        adminLogin(request);
        City c = cityService.edit(city);
        return ResponseEntity.ok(c);
    }

    @GetMapping("/cities/{id}")
    public ResponseEntity<CityWithCinemasDTO> getById(@PathVariable int id){
             return ResponseEntity.ok(cityService.getCityById(id));
    }

    @GetMapping("/cities")
    public List getAll(){
         return cityService.getAll();
    }


}
