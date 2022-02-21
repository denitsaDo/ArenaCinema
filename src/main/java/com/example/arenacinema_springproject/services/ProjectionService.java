package com.example.arenacinema_springproject.services;

import com.example.arenacinema_springproject.exceptions.BadRequestException;
import com.example.arenacinema_springproject.exceptions.NotFoundException;
import com.example.arenacinema_springproject.models.dto.ProjectionAddDTO;
import com.example.arenacinema_springproject.models.entities.Projection;
import com.example.arenacinema_springproject.models.repositories.HallRepository;
import com.example.arenacinema_springproject.models.repositories.MovieRepository;
import com.example.arenacinema_springproject.models.repositories.ProjectionRepository;
import com.example.arenacinema_springproject.models.repositories.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Projection addProjection(ProjectionAddDTO projection) {

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
            return p;

    }

    public Projection getProjectionById(int id) {
        return  projectionRepository.findById(id).orElseThrow(()-> new NotFoundException("Projection not found"));
    }
}
