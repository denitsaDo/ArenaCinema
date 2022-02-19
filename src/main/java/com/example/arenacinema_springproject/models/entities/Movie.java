package com.example.arenacinema_springproject.models.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="movies")
@Getter
@Setter
@NoArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String title;
    @Column
    private int duration;
    @Column
    private String description;
    @Column
    private String actors;
    @Column
    private LocalDate premiere;
    @Column
    private String director;
    @Column
    private int category_id;
    @Column
    private String poster_url;


}
