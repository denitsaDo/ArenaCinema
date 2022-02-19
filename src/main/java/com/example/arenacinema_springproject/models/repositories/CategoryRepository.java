package com.example.arenacinema_springproject.models.repositories;

import com.example.arenacinema_springproject.models.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Category findByName(String name);
    Category findByDescription(String description);
}
