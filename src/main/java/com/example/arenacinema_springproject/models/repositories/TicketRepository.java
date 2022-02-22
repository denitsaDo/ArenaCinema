package com.example.arenacinema_springproject.models.repositories;

import com.example.arenacinema_springproject.models.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {


}
