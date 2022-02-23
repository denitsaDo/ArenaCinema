package com.example.arenacinema_springproject.models.repositories;

import com.example.arenacinema_springproject.models.dto.TicketAddDTO;
import com.example.arenacinema_springproject.models.entities.Projection;
import com.example.arenacinema_springproject.models.entities.Ticket;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    List<Ticket> findAllByOwnerId(int id);

    Optional<Ticket> findTicketByProjectionIdForTicketAndRownumberAndSeatNumber(Projection projection, int row, int seat);

}
