package com.example.arenacinema_springproject.services;


import com.example.arenacinema_springproject.exceptions.BadRequestException;
import com.example.arenacinema_springproject.exceptions.NotFoundException;
import com.example.arenacinema_springproject.models.dto.HallAddDTO;
import com.example.arenacinema_springproject.models.entities.Hall;
import com.example.arenacinema_springproject.models.repositories.CinemaRepository;
import com.example.arenacinema_springproject.models.repositories.HallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HallService {

    @Autowired
    private HallRepository hallRepository;

    @Autowired
    private CinemaRepository cinemaRepository;

    public Hall addHall(HallAddDTO hall) {
        if (hall.getName() == null || hall.getName().isBlank()) {
            throw new BadRequestException("Cinema name is mandatory!");
        }
        if (hall.getCapacity() < 25 || hall.getCapacity() > 120) {
            throw new BadRequestException("Seats must be between 25 and 120");
        }

        Hall h = new Hall();
        h.setName(hall.getName());
        h.setCapacity(hall.getCapacity());
        h.setCinemaIn(cinemaRepository.findById(hall.getCinemaId()).orElseThrow(() -> new NotFoundException("Cinema not found")));
        hallRepository.save(h);
        return h;
    }

    public Hall getHallById(int id) {
        return hallRepository.findById(id).orElseThrow(() -> new NotFoundException("Hall not found"));
    }

    public void delete(Hall hall) {
        hallRepository.delete(hallRepository.findById(hall.getId()).orElseThrow(() -> new NotFoundException("No such hall.")));
    }

    public Hall edit(Hall hall) {
        Optional<Hall> opt = hallRepository.findById(hall.getId());
        if(opt.isPresent()){
            hallRepository.save(hall);
            return hall;
        }
        else{
            throw new NotFoundException("Hall not found.");
        }
    }

    public List getAllHalls() {
        return hallRepository.findAll();
    }
}