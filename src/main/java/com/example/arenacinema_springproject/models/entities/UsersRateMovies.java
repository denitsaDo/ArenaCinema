package com.example.arenacinema_springproject.models.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Set;

@Entity
@Table(name = "users_rate_movies")
@Getter
@Setter
@NoArgsConstructor
public class UsersRateMovies {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userRatesMovie;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movieRatedByUser;

    @Column
    private int rating;

}
