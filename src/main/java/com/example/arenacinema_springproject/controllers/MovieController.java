package com.example.arenacinema_springproject.controllers;

import com.example.arenacinema_springproject.models.dto.MovieAddDTO;
import com.example.arenacinema_springproject.models.dto.MovieResponseDTO;
import com.example.arenacinema_springproject.models.entities.Movie;
import com.example.arenacinema_springproject.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@RestController
public class MovieController extends BaseController {

    @Autowired
    private MovieService movieService;

    @PostMapping("/movies")
    public ResponseEntity<MovieResponseDTO> addMovie(@Valid @RequestBody MovieAddDTO movie, HttpServletRequest request) {
        validateLogin(request);
        adminLogin(request);
        return ResponseEntity.ok(movieService.add(movie));
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

    //TODO add getRatingById
}
