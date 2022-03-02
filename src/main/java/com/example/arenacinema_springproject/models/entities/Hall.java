package com.example.arenacinema_springproject.models.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "halls")
@Getter
@Setter
@NoArgsConstructor
public class Hall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;
    @Column
    private int rowsNumber;
    @Column
    private int seatsPerRow;
    @ManyToOne
    @JoinColumn(name = "cinema_id")
    private Cinema cinemaIn;

    @OneToMany(mappedBy = "hallForProjection")
    private Set<Projection> projections;
}
