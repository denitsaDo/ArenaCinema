package com.example.arenacinema_springproject.services;


import com.example.arenacinema_springproject.exceptions.BadRequestException;
import com.example.arenacinema_springproject.exceptions.NotFoundException;
import com.example.arenacinema_springproject.models.dto.CinemaWithoutHallDTO;
import com.example.arenacinema_springproject.models.dto.HallAddDTO;
import com.example.arenacinema_springproject.models.dto.HallEditDTO;
import com.example.arenacinema_springproject.models.dto.HallWithCinemaDTO;
import com.example.arenacinema_springproject.models.entities.Hall;
import com.example.arenacinema_springproject.models.repositories.CinemaRepository;
import com.example.arenacinema_springproject.models.repositories.HallRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HallService {

    @Autowired
    private HallRepository hallRepository;
    @Autowired
    private CinemaRepository cinemaRepository;
    @Autowired
    private ModelMapper modelMapper;

    public HallWithCinemaDTO addHall(HallAddDTO hall) {
        validateHallData(hall);

        Hall h = new Hall();
        h.setName(hall.getName());
        h.setRowsNumber(hall.getRowsNumber());
        h.setSeatsPerRow(hall.getSeatsPerRow());
        h.setCinemaIn(cinemaRepository.findById(hall.getCinemaId()).orElseThrow(() -> new NotFoundException("Cinema not found")));
        hallRepository.save(h);
        HallWithCinemaDTO dto = new HallWithCinemaDTO();
        modelMapper.map(h,dto);
        dto.setCinemaForThisHall(modelMapper.map(h.getCinemaIn(), CinemaWithoutHallDTO.class));
        return dto;

    }


    public HallWithCinemaDTO getHallById(int id) {
        Hall h = hallRepository.findById(id).orElseThrow(() -> new NotFoundException("Hall not found"));
        HallWithCinemaDTO dto = new HallWithCinemaDTO();
        dto.setId(h.getId());
        dto.setName(h.getName());
        dto.setRowsNumber(h.getRowsNumber());
        dto.setSeatsPerRow(h.getSeatsPerRow());
        dto.setCinemaForThisHall(modelMapper.map(h.getCinemaIn(), CinemaWithoutHallDTO.class));
        return dto;
    }


    public void delete(int id) {
        hallRepository.delete(hallRepository.findById(id).orElseThrow(() -> new NotFoundException("No such hall.")));
    }

    public HallWithCinemaDTO edit(HallEditDTO hall) {
        validateHallData(modelMapper.map(hall, HallAddDTO.class));
        Optional<Hall> opt = hallRepository.findById(hall.getId());
        if(opt.isPresent()){
            Hall h = modelMapper.map(hall, Hall.class);
            h.setCinemaIn(cinemaRepository.findById(hall.getCinemaId()).orElseThrow(()-> new BadRequestException("No such cinema")));
            hallRepository.save(h);
            HallWithCinemaDTO dto = new HallWithCinemaDTO();
            modelMapper.map(h,dto);
            dto.setCinemaForThisHall(modelMapper.map(h.getCinemaIn(), CinemaWithoutHallDTO.class));
            return dto;
        }
        else{
            throw new NotFoundException("Hall not found.");
        }
    }

    private void validateHallData(HallAddDTO hall) {
        if (hall.getName() == null || hall.getName().isBlank()) {
            throw new BadRequestException("Cinema name is mandatory!");
        }
        if (hall.getRowsNumber() <= 0 ||  hall.getSeatsPerRow() <= 0) {
            throw new BadRequestException("Seats and rows should be greater than 0.");
        }
    }

}