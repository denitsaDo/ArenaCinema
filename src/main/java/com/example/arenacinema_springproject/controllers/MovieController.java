package com.example.arenacinema_springproject.controllers;

import com.example.arenacinema_springproject.models.dto.MovieAddDTO;
import com.example.arenacinema_springproject.models.dto.MovieEditDTO;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@RestController
public class MovieController extends BaseController {

    @Autowired
    private MovieService movieService;

    @SneakyThrows
    @PostMapping("/movies")
    public ResponseEntity<MovieResponseDTO> addMovie(@RequestBody MovieAddDTO movie, HttpServletRequest request) {
        validateLogin(request);
        adminLogin(request);
        MovieResponseDTO dto = movieService.add(movie);
        return ResponseEntity.ok(dto);
    }


    @PutMapping("/movies")
    public ResponseEntity<MovieResponseDTO> edit(@RequestBody MovieEditDTO movie, HttpServletRequest request){
        validateLogin(request);
        adminLogin(request);
        MovieResponseDTO movie1 = movieService.edit(movie);
        return ResponseEntity.ok(movie1);
    }

    @DeleteMapping("/movies/{id}")
    public void deleteMovie(@Valid @PathVariable int id, HttpServletRequest request) {
        validateLogin(request);
        adminLogin(request);;
        movieService.delete(id);
    }

    @GetMapping("/movies/{id}")
    public MovieResponseDTO getById(@PathVariable int id) {
        return movieService.getById(id);
    }

    @SneakyThrows
    @PostMapping("/movies/poster")
    public String uploadPoster(@RequestParam(name = "file") MultipartFile file,@RequestParam int id, HttpServletRequest request){
        validateLogin(request);
        adminLogin(request);
        return movieService.uploadFile(file, id);
    }

}
