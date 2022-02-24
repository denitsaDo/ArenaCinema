package com.example.arenacinema_springproject.models.repositories;


import com.example.arenacinema_springproject.models.entities.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CinemaRepository extends JpaRepository<Cinema, Integer> {


    Cinema findFirstByName(String name);

}
