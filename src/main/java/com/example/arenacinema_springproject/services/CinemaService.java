package com.example.arenacinema_springproject.services;

import com.example.arenacinema_springproject.exceptions.BadRequestException;
import com.example.arenacinema_springproject.exceptions.NoContentException;
import com.example.arenacinema_springproject.exceptions.NotFoundException;
import com.example.arenacinema_springproject.models.dto.CinemaAddDTO;
import com.example.arenacinema_springproject.models.entities.Cinema;
import com.example.arenacinema_springproject.models.repositories.CinemaRepository;
import com.example.arenacinema_springproject.models.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CinemaService {

    @Autowired
    private CinemaRepository cinemaRepository;
    @Autowired
    private CityRepository cityRepository;


    public Cinema add(CinemaAddDTO cinema) {
        if (cinema.getName() == null || cinema.getName().isBlank()) {
            throw new BadRequestException("Cinema name is mandatory!");
        }
        if (cinemaRepository.findByName(cinema.getName())!= null) {
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
        return c;
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
