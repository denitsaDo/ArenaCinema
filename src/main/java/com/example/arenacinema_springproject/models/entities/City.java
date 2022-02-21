package com.example.arenacinema_springproject.models.entities;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "cities")
@Getter
@Setter
@NoArgsConstructor
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;
    @OneToMany(mappedBy = "citySelected", cascade = CascadeType.ALL)
    //@JsonManagedReference    //overcomes circular reference problem   city->cinema but doesn`t give needed functionality
    private Set<Cinema> townCinemas;
}
