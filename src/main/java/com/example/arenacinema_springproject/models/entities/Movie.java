package com.example.arenacinema_springproject.models.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

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
    private String poster_url;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "movieForProjection")
    private Set<Projection> projections;

    @OneToMany(mappedBy = "movieRatedByUser")
    Set<UsersRateMovies> ratings;


}
