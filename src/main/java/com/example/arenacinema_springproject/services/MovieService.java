package com.example.arenacinema_springproject.services;

import com.example.arenacinema_springproject.exceptions.BadRequestException;
import com.example.arenacinema_springproject.exceptions.NotFoundException;
import com.example.arenacinema_springproject.models.dto.MovieAddDTO;
import com.example.arenacinema_springproject.models.dto.MovieResponseDTO;
import com.example.arenacinema_springproject.models.entities.Movie;
import com.example.arenacinema_springproject.models.repositories.MovieRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ModelMapper modelMapper;

    public MovieResponseDTO add(MovieAddDTO movieAddDTO){
        validateMovie(movieAddDTO);
        Movie movie1 = new Movie();
        movie1.setActors(movieAddDTO.getActors());
        movie1.setCategory(movieAddDTO.getCategory());
        movie1.setDescription(movieAddDTO.getDescription());
        movie1.setDuration(movieAddDTO.getDuration());
        movie1.setPremiere(movieAddDTO.getPremiere());
        movie1.setPoster_url(movieAddDTO.getPoster_url());
        movie1.setTitle(movieAddDTO.getTitle());
        movie1.setDirector(movieAddDTO.getDirector());
        movieRepository.save(movie1);
        MovieResponseDTO dto = modelMapper.map(movieAddDTO, MovieResponseDTO.class);
        return dto;

    }

    public Movie getById(int id) {
       Optional<Movie> movie =  movieRepository.findById(id);
       if (movie.isPresent()){
           return movie.get();
       }else {
           throw new NotFoundException("Movie not found.");
       }
    }

    public void delete(Movie movie) {
        Movie movie1 = movieRepository.getById(movie.getId());
        movieRepository.delete(movie1);
    }

    public Movie edit(Movie movie) {
        Optional<Movie> movie1 = movieRepository.findById(movie.getId());
        if (movie1.isPresent()) {
            movieRepository.save(movie);
            return movie;
        } else {
            throw new NotFoundException("Movie not found.");
        }
    }

    private MovieAddDTO validateMovie(MovieAddDTO movieAddDTO){
        if (movieAddDTO.getTitle() == null || movieAddDTO.getTitle().isBlank()) {
            throw new BadRequestException("Movie title is mandatory!");
        }
        if (movieAddDTO.getDuration()<=0 || movieAddDTO.getDuration() > 240){
            throw new BadRequestException("Movie duration is too short or long!");
        }
        if (movieAddDTO.getDescription() == null || movieAddDTO.getDescription().isBlank()) {
            throw new BadRequestException("Movie description is mandatory!");
        }
        if (movieRepository.findByDescription(movieAddDTO.getDescription())!=null){
            throw new BadRequestException("Movie already exists!");
        }
        if (movieAddDTO.getActors() == null || movieAddDTO.getActors().isBlank()) {
            throw new BadRequestException("Movie actors is mandatory!");
        }
        LocalDate date = LocalDate.now();
        if (movieAddDTO.getPremiere().isBefore(date)){
            throw new BadRequestException("Invalid premiere date!");
        }
        if (movieAddDTO.getDirector() == null || movieAddDTO.getDirector().isBlank()) {
            throw new BadRequestException("Movie director is mandatory!");
        }
        if (movieAddDTO.getPoster_url() == null || movieAddDTO.getPoster_url().isBlank()) {
            throw new BadRequestException("Movie poster is mandatory!");
        }
        return movieAddDTO;
    }


}
