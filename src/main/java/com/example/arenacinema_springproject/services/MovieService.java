package com.example.arenacinema_springproject.services;

import com.example.arenacinema_springproject.exceptions.BadRequestException;
import com.example.arenacinema_springproject.exceptions.NotFoundException;
import com.example.arenacinema_springproject.models.dto.*;
import com.example.arenacinema_springproject.models.entities.Movie;
import com.example.arenacinema_springproject.models.repositories.CategoryRepository;
import com.example.arenacinema_springproject.models.repositories.MovieRepository;

import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.apache.tika.Tika;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.nio.file.Files;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static final int MIN_LENGTH = 2;
    public static final int MAX_LENGTH = 100;

    public MovieResponseDTO add(MovieAddDTO movieAddDTO){
        validateMovie(movieAddDTO);
        Movie movie1 = new Movie();
        movie1.setActors(movieAddDTO.getActors());
        movie1.setCategory(categoryRepository.findById(movieAddDTO.getCategoryId()).orElseThrow(() -> new NotFoundException("Category not found!")));
        movie1.setDescription(movieAddDTO.getDescription());
        movie1.setDuration(movieAddDTO.getDuration());
        movie1.setPremiere(movieAddDTO.getPremiere());
        movie1.setTitle(movieAddDTO.getTitle());
        movie1.setDirector(movieAddDTO.getDirector());
        movieRepository.save(movie1);
        MovieResponseDTO dto = modelMapper.map(movieAddDTO, MovieResponseDTO.class);
        return dto;
    }

    public MovieResponseDTO getById(int id) {
        Movie m = movieRepository.findById(id).orElseThrow(()-> new NotFoundException("Movie not found!"));
        MovieResponseDTO dto = modelMapper.map(m, MovieResponseDTO.class);
        return  dto;
    }

    public void delete(int id) {
        movieRepository.delete(movieRepository.findById(id).orElseThrow(() -> new NotFoundException("Movie not found")));
    }

    public MovieResponseDTO edit(MovieEditDTO movie) {
        if (movie.getTitle() == null || movie.getTitle().isBlank()
                || movie.getTitle().length() < MIN_LENGTH || movie.getTitle().length() > MAX_LENGTH) {
            throw new BadRequestException("Movie title is mandatory!");
        }
        if (movie.getDuration()<=0 || movie.getDuration() > 240){
            throw new BadRequestException("Movie duration is too short or long!");
        }
        if (movie.getDescription() == null || movie.getDescription().isBlank()
                || movie.getDescription().length() > MAX_LENGTH*3) {
            throw new BadRequestException("Movie description is mandatory or is too long!");
        }
        if (movie.getActors() == null || movie.getActors().isBlank()
                || movie.getActors().length() > MAX_LENGTH*2) {
            throw new BadRequestException("Movie actors is mandatory!");
        }
        LocalDate date = LocalDate.now();
        if (movie.getPremiere().isBefore(date)){
            throw new BadRequestException("Invalid premiere date!");
        }
        if (movie.getDirector() == null || movie.getDirector().isBlank()
                || movie.getDirector().length() > MAX_LENGTH) {
            throw new BadRequestException("Movie director is mandatory!");
        }
        if (movie.getPoster_url() == null || movie.getPoster_url().isBlank()) {
            throw new BadRequestException("Movie poster is mandatory!");
        }

        Optional<Movie> opt = movieRepository.findById(movie.getId());
        if (opt.isPresent()) {
            Movie movie1 = new Movie();
            movie1.setTitle(movie.getTitle());
            movie1.setId(movie.getId());
            movie1.setActors(movie.getActors());
            movie1.setDescription(movie.getDescription());
            movie1.setDuration(movie.getDuration());
            movie1.setPremiere(movie.getPremiere());
            movie1.setCategory(categoryRepository.getById(movie.getCategoryId()));
            movie1.setPoster_url(movie.getPoster_url());
            movie1.setDirector(movie.getDirector());
            movieRepository.save(movie1);
            MovieResponseDTO dto = modelMapper.map(movie1, MovieResponseDTO.class);
            return dto;
        }
        else {
            throw new NotFoundException("Movie not found!");
        }
    }


    private MovieAddDTO validateMovie(MovieAddDTO movieAddDTO){
        if (movieAddDTO.getTitle() == null || movieAddDTO.getTitle().isBlank()
        || movieAddDTO.getTitle().length() < MIN_LENGTH || movieAddDTO.getTitle().length() > MAX_LENGTH) {
            throw new BadRequestException("Movie title is mandatory!");
        }
        if (movieAddDTO.getDuration()<=0 || movieAddDTO.getDuration() > 240){
            throw new BadRequestException("Movie duration is too short or long!");
        }
        if (movieAddDTO.getDescription() == null || movieAddDTO.getDescription().isBlank()
        || movieAddDTO.getDescription().length() > MAX_LENGTH*3) {
            throw new BadRequestException("Movie description is mandatory or is too long!");
        }
        if (movieRepository.findByDescription(movieAddDTO.getDescription())!=null){
            throw new BadRequestException("Movie already exists!");
        }
        if (movieAddDTO.getActors() == null || movieAddDTO.getActors().isBlank()
        || movieAddDTO.getActors().length() > MAX_LENGTH*2) {
            throw new BadRequestException("Movie actors is mandatory!");
        }
//        LocalDate date = LocalDate.now();
//        if (movieAddDTO.getPremiere().isBefore(date)){
//            throw new BadRequestException("Invalid premiere date!");
//        }
        if (movieAddDTO.getDirector() == null || movieAddDTO.getDirector().isBlank()
        || movieAddDTO.getDirector().length() > MAX_LENGTH) {
            throw new BadRequestException("Movie director is mandatory!");
        }
        return movieAddDTO;
    }

    @SneakyThrows
    public String uploadFile(MultipartFile file, int id) {
        validateFile(file);
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String name = System.nanoTime() + "." + extension;
        Files.copy(file.getInputStream(), new File("/Users/dezizlava/Desktop/ArenaCinema/uploads" + File.separator + name).toPath());
        Movie m = movieRepository.getById(id);
        m.setPoster_url(name);
        movieRepository.save(m);
        return name;
    }

    public MovieResponseRatingDTO getRatingByMovieId(int movieId) {
        if (!movieRepository.findById(movieId).isPresent()) {
            throw new BadRequestException("No movie with this id");
        }
        String sql = "SELECT ROUND (AVG(r.rating), 0)\n" +
                "FROM users_rate_movies AS r \n" +
                "JOIN movies AS m ON r.movie_id = m.id\n" +
                "WHERE m.id = ?";
        MovieResponseRatingDTO ratedMovie = jdbcTemplate.queryForObject(sql,new MovieRowMapper(),new Object[] {movieId});

        return ratedMovie;
    }

    private class MovieRowMapper implements RowMapper<MovieResponseRatingDTO> {
        @Override
        public MovieResponseRatingDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            MovieResponseRatingDTO movie = new MovieResponseRatingDTO();
            movie.setRating(rs.getInt(1));
            return movie;
        }
    }

    @SneakyThrows
    public static void validateFile(MultipartFile multipartFile) {
        Tika tika = new Tika();

        String detectedType = tika.detect(multipartFile.getInputStream());

        if (!detectedType.contains("image")){
            throw new BadRequestException("Wrong media type.");
        }

    }

}
