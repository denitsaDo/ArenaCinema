package com.example.arenacinema_springproject.models.repositories;


import com.example.arenacinema_springproject.models.entities.Cinema;
import com.example.arenacinema_springproject.models.entities.UsersRateMovies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRateMoviesRepository extends JpaRepository<UsersRateMovies, Integer> {

    Optional <UsersRateMovies> findByUserRatesMovieIdAndMovieRatedByUserId(int movieId, int userId);
}
