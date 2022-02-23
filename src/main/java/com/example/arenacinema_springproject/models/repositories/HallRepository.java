package com.example.arenacinema_springproject.models.repositories;

import com.example.arenacinema_springproject.models.entities.Hall;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HallRepository extends JpaRepository<Hall, Integer> {


}
