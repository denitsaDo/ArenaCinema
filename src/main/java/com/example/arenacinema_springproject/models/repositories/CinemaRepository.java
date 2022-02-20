package com.example.arenacinema_springproject.models.repositories;


import com.example.arenacinema_springproject.models.entities.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CinemaRepository extends JpaRepository<Cinema, Integer> {

    Cinema findByName(String name);

    Optional<List<Cinema>> findAllByCitySelected_Name(String name);

  // @Query("select cin.id, cin.name from cinemas as cin left outer join cities as c on cin.city_id=c.id where c.name= burgas " )

}
