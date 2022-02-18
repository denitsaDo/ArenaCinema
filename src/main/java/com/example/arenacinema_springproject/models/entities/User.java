package com.example.arenacinema_springproject.models.entities;


import com.example.arenacinema_springproject.exceptions.BadRequestException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Date;


@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@ToString
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
    @Email(message = "Invalid email!") //added validation dependency
    private String email;
    @Column
    private String password;
    @Column
    private Date dateOfBirth;
    @Column
    private boolean isAdmin;


}
