package com.example.arenacinema_springproject.models.repositories;

import com.example.arenacinema_springproject.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    public User findByEmailAndPassword(String email, String password);
}
