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

import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ModelMapper modelMapper;

    public MovieResponseDTO add(MovieAddDTO movieAddDTO){
        Movie movie = movieRepository.findByTitle(movieAddDTO.getTitle());
        //Movie movie2 = movieRepository.exists()
        if (movie != null) {
            throw new BadRequestException("This movie already exists");
        }
        //TODO more validates
        Movie movie1 = new Movie();
        movie1.setActors(movieAddDTO.getActors());
        movie1.setCategory_id(movieAddDTO.getCategory_id());
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
}
