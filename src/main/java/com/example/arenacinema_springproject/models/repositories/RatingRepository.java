package com.example.arenacinema_springproject.models.repositories;

import com.example.arenacinema_springproject.models.entities.MovieRating;
import com.example.arenacinema_springproject.models.entities.UserRateMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<MovieRating , UserRateMovie> {
}
