package com.example.arenacinema_springproject.models.repositories;

import com.example.arenacinema_springproject.models.dto.TicketAddDTO;
import com.example.arenacinema_springproject.models.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    List<Ticket> findAllByOwnerId(int id);
}
