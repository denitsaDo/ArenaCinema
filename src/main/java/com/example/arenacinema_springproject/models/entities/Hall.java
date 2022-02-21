package com.example.arenacinema_springproject.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    private int capacity;
    @ManyToOne
    @JoinColumn(name = "cinema_id")
    //@JsonBackReference
    private Cinema cinemaIn;

    @OneToMany(mappedBy = "hallForProjection")
    //@JsonManagedReference  //overcomes circular reference problem in city->cinema but doesn`t give needed functionality
    private Set<Projection> projections;
}
