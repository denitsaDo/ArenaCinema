package com.example.arenacinema_springproject.services;

import com.example.arenacinema_springproject.exceptions.BadRequestException;
import com.example.arenacinema_springproject.exceptions.NoContentException;
import com.example.arenacinema_springproject.exceptions.NotFoundException;
import com.example.arenacinema_springproject.models.entities.Cinema;
import com.example.arenacinema_springproject.models.entities.City;
import com.example.arenacinema_springproject.models.repositories.CinemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CinemaService {

    @Autowired
    private CinemaRepository cinemaRepository;


    public Cinema add(String name, Integer cityId, String phoneNumber, String address, String email) {
        if (name == null || name.isBlank()) {
            throw new BadRequestException("Cinema name is mandatory!");
        }
        if (cinemaRepository.findByName(name)!= null) {
            throw new BadRequestException("Cinema already exists!");
        }
        if (cityId == null || cityId == 0) {
            throw new BadRequestException("City is mandatory!");
        }
        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new BadRequestException("Phone number is mandatory!");
        }
        if (!phoneNumber.matches("[0-9]{10}")) {
            throw new BadRequestException("Invalid phone number!");
        }
        if (address == null || address.isBlank()) {
            throw new BadRequestException("Address is mandatory!");
        }
        if (email == null || email.isBlank()) {
            throw new BadRequestException("Email is mandatory!");
        }

        Cinema c = new Cinema();
        c.setName(name);
        c.setCitySelected(new City());
        c.setPhoneNumber(phoneNumber);
        c.setAddress(address);
        c.setEmail(email);
        cinemaRepository.save(c);
        return c;
    }

    public Cinema getCinemaById(int id) {
//        return  cinemaRepository.findById(id).orElseThrow(()-> new NotFoundException("Cinema not found"));
        Optional<Cinema> cinema = cinemaRepository.findById(id);
        if (cinema.isPresent()) {
            return cinema.get();
        }
        else {
            throw new NotFoundException("Cinema not found");
        }
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

    public Cinema edit(Cinema cinema) {
        Optional<Cinema> opt = cinemaRepository.findById(cinema.getId());
        if(opt.isPresent()){
            cinemaRepository.save(cinema);
            return cinema;
        }
        else{
            throw new NotFoundException("Cinema not found.");
        }
    }

//    public List getAllByCityId(int cityId) {
//        Optional<Cinema> opt = cinemaRepository.findByCityId(cityId);
//        if(opt.isPresent()){
//            Cinema cinema = opt.get();
//            List<Cinema> townCinemas = new ArrayList<>();
//            townCinemas.add(cinema);
//            return townCinemas;
//        }
//        else{
//            throw new NotFoundException("No cinemas in this town.");
//        }
//    }
}
