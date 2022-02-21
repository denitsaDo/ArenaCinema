package com.example.arenacinema_springproject.models.repositories;


import com.example.arenacinema_springproject.models.entities.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CinemaRepository extends JpaRepository<Cinema, Integer> {


    Optional<List<Cinema>> findByName(String name);

}
