package com.example.arenacinema_springproject.controllers;

import com.example.arenacinema_springproject.models.dto.MovieAddDTO;
import com.example.arenacinema_springproject.models.dto.MovieResponseDTO;
import com.example.arenacinema_springproject.models.entities.Movie;
import com.example.arenacinema_springproject.models.repositories.CategoryRepository;
import com.example.arenacinema_springproject.models.repositories.MovieRepository;
import com.example.arenacinema_springproject.services.MovieService;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@RestController
public class MovieController extends BaseController {

    @Autowired
    private MovieService movieService;

    @SneakyThrows
    @PostMapping("/movies")
    public ResponseEntity<MovieResponseDTO> addMovie(@RequestBody MovieAddDTO movie, HttpServletRequest request) {
        //validateLogin(request);
        //adminLogin(request);
        MovieResponseDTO dto = movieService.add(movie);
        return ResponseEntity.ok(dto);
    }


    @PutMapping("/movies")
    public ResponseEntity<Movie> edit(@RequestBody Movie movie, HttpServletRequest request){
        validateLogin(request);
        adminLogin(request);
        Movie movie1 = movieService.edit(movie);
        return ResponseEntity.ok(movie1);
    }

    @DeleteMapping("/movies/{id}")
    public void deleteMovie(@Valid @PathVariable int id, HttpServletRequest request) {
        validateLogin(request);
        adminLogin(request);
        Movie movie = movieService.getById(id);
        movieService.delete(movie);
    }

    @GetMapping("/movies/{id}")
    public Movie getById(@PathVariable int id, HttpServletRequest request) {
        return movieService.getById(id);
    }


    //TODO add getRatingById (SELECT AVG(r.rating) FROM users_rate_movies AS r JOIN movies AS m ON r.movie_id = m.id
    //JOIN users AS u ON r.user_id = u.id
    //WHERE m.id = ? AND u.id = ?;)



}
