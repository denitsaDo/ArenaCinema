package com.example.arenacinema_springproject.services;

import com.example.arenacinema_springproject.controllers.TicketController;
import com.example.arenacinema_springproject.exceptions.BadRequestException;
import com.example.arenacinema_springproject.exceptions.NoContentException;
import com.example.arenacinema_springproject.exceptions.NotFoundException;
import com.example.arenacinema_springproject.models.dto.*;
import com.example.arenacinema_springproject.models.entities.Cinema;
import com.example.arenacinema_springproject.models.entities.Projection;
import com.example.arenacinema_springproject.models.repositories.HallRepository;
import com.example.arenacinema_springproject.models.repositories.MovieRepository;
import com.example.arenacinema_springproject.models.repositories.ProjectionRepository;
import com.example.arenacinema_springproject.models.repositories.TypeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ProjectionService {

    @Autowired
    private ProjectionRepository projectionRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private HallRepository hallRepository;
    @Autowired
    private TypeRepository typeRepository;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private ModelMapper modelMapper;

    public ProjectionResponseDTO addProjection(ProjectionAddDTO projection) {

        if (projection.getMovieId() == 0 || projection.getHallId() == 0 || projection.getTypeId() == 0) {
                throw new BadRequestException("Movie, hall and type are mandatory fields!");
            }
        if (projection.getStartTime() == null ) {
                throw new BadRequestException("Projection start time is mandatory!");
            }

        Projection p = new Projection();
        p.setMovieForProjection(movieRepository.findById(projection.getMovieId()).orElseThrow(()-> new NotFoundException("Movie not found")));
        p.setHallForProjection(hallRepository.findById(projection.getHallId()).orElseThrow(()-> new NotFoundException("Hall not found")));
        p.setTypeForProjection(typeRepository.findById(projection.getTypeId()).orElseThrow(()-> new NotFoundException("Type not found")));
        p.setStartTime(projection.getStartTime());

        projectionRepository.save(p);
        ProjectionResponseDTO dto = modelMapper.map(p, ProjectionResponseDTO.class);
        return dto;
    }

    public ProjectionByIdDTO getProjectionById(int id) {
        Projection p = projectionRepository.findById(id).orElseThrow(()-> new NotFoundException("Projection not found"));
        ProjectionByIdDTO dto = new ProjectionByIdDTO();
        dto.setMovieName(p.getMovieForProjection().getTitle());
        dto.setStartTime(p.getStartTime());
        dto.setSeats(ticketService.getSeatsForProjection(id));
        return  dto;
    }

    public ProjectionResponseDTO editProjection(ProjectionEditDTO projection) {
        if (projection.getId() == 0) {
            throw new BadRequestException("Projection id is mandatory field!");
        }
        if (projection.getMovieId() == 0 || projection.getHallId() == 0 || projection.getTypeId() == 0) {
            throw new BadRequestException("Movie, hall and type are mandatory fields!");
        }
        if (projection.getStartTime() == null) {
            throw new BadRequestException("Projection start time is mandatory!");
        }

        Optional<Projection> opt = projectionRepository.findById(projection.getId());
        if (opt.isPresent()) {

            Projection modifiedProjection = new Projection();
            modifiedProjection.setId(projection.getId());
            modifiedProjection.setMovieForProjection(movieRepository.findById(projection.getMovieId()).orElseThrow());
            modifiedProjection.setHallForProjection(hallRepository.findById(projection.getHallId()).orElseThrow());
            modifiedProjection.setTypeForProjection(typeRepository.findById(projection.getTypeId()).orElseThrow());
            modifiedProjection.setStartTime( projection.getStartTime());
            modifiedProjection.setProjectionTickets(opt.get().getProjectionTickets());
            projectionRepository.save(modifiedProjection);
            ProjectionResponseDTO dto = new ProjectionResponseDTO();
            modelMapper.map(modifiedProjection, dto);
            return dto;
        } else {
            throw new NotFoundException("Projection not found.");
        }
    }


    public void delete(int id) {
        Optional<Projection> optional = projectionRepository.findById(id);
        if (optional.isPresent() ) {
            projectionRepository.delete(optional.get());
            throw new NoContentException();
        }
        else {
            throw new NotFoundException("No such projection!");
        }
    }

}
