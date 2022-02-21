package com.example.arenacinema_springproject.models.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "types")
@Getter
@Setter
@NoArgsConstructor
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;
//    @OneToMany(mappedBy = "citySelected")
//
//    private Set<Cinema> townCinemas;

    @OneToMany(mappedBy = "typeForProjection")
    private Set<Projection> projections;
}
