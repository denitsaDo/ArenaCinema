package com.example.arenacinema_springproject.models.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userForTicket;

    @ManyToOne
    @JoinColumn(name = "projection_id")
    private Projection projectionIdForTicket;

    @Column
    private int rowNumber;

    @Column
    private int seatNumber;


}
