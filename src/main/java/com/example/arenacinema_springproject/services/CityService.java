package com.example.arenacinema_springproject.services;

import com.example.arenacinema_springproject.exceptions.BadRequestException;
import com.example.arenacinema_springproject.exceptions.NoContentException;
import com.example.arenacinema_springproject.exceptions.NotFoundException;
import com.example.arenacinema_springproject.models.entities.City;
import com.example.arenacinema_springproject.models.entities.User;
import com.example.arenacinema_springproject.models.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    public City add(String name) {
        if (name == null || name.isBlank()){
            throw new BadRequestException("City name is mandatory!");
        }
        if (cityRepository.findByName(name)!= null) {
            throw new BadRequestException("City already exists!");
        }
        City c = new City();
        c.setName(name);
        cityRepository.save(c);
        return c;
    }

    public City getById(int id) {
        Optional<City> city = cityRepository.findById(id);
        if (city.isPresent()) {
            return city.get();
        }
        else {
            throw new NotFoundException("City not found");
        }
    }

    public void delete(City city) {
        Optional<City> optional = cityRepository.findById(city.getId());   //check if id is >0
        if (optional.isPresent() ) {
            cityRepository.delete(city);
            throw new NoContentException();
        }
        else {
            throw new NotFoundException("No such city.");
        }
    }

    public City edit(City city) {
        Optional<City> opt = cityRepository.findById(city.getId());
        if(opt.isPresent()){
            cityRepository.save(city);
            return city;
        }
        else{
            throw new NotFoundException("City not found.");
        }
    }

    public City getCityById(int id) {
        Optional<City> city = cityRepository.findById(id);
        if (city.isPresent()) {
            return city.get();
        }
        else {
            throw new NotFoundException("City not found");
        }
    }

    public List<City> getAll() {
       return cityRepository.findAll();
    }
}
