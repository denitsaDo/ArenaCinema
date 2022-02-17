package com.example.arenacinema_springproject.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class UserRegisterDTO {

    private String firstName;
    private String secondName;
    private String lastName;
    private String gender;
    private String email;
    private String password;
    private String password2;
    private Date dateOfBirth;
}
