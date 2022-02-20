package com.example.arenacinema_springproject.models.repositories;

import com.example.arenacinema_springproject.models.entities.City;
import com.example.arenacinema_springproject.models.entities.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRepository extends JpaRepository<Type, Integer> {

    Type findByName(String name);
}
