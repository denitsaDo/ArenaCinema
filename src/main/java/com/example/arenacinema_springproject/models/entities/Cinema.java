package com.example.arenacinema_springproject.models.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Set;

@Entity
@Table(name = "cinemas")
@Getter
@Setter
@NoArgsConstructor
public class Cinema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;
    @ManyToOne
    @JoinColumn(name = "city_id")
    //@JsonBackReference     //this helps to overcome circular reference problem   city->cinema
    private City citySelected;
    @Column
    private String phoneNumber;
    @Column
    private String address;
    @Column
    @Email(message = "Invalid email!")
    private String email;

    @OneToMany(mappedBy = "cinemaIn")
    //@JsonManagedReference  //overcomes circular reference problem in city->cinema but doesn`t give needed functionality
    private Set<Hall> halls;
}
