package com.example.arenacinema_springproject.models.entities;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Date;


@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String firstName;
    @Column
    private String secondName;
    @Column
    private String lastName;
    @Column
    private String gender;
    @Column
    @Email                       //added validation dependency
    private String email;
    @Column
    private String password;
    @Column
    private Date dateOfBirth;
    @Column
    private boolean isAdmin;
}
