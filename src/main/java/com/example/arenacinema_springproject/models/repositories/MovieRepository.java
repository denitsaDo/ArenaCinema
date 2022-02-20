package com.example.arenacinema_springproject.models.repositories;

import com.example.arenacinema_springproject.models.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MovieRepository extends JpaRepository<Movie, Integer> {

    Movie findByDescription(String description);

}
