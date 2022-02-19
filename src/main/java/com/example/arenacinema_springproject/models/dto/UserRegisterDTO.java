package com.example.arenacinema_springproject.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class UserRegisterDTO {

    private String firstName;
    private String secondName;
    private String lastName;
    private String gender;
    @Email(message = "Invalid email!") //added validation dependency
    private String email;
    private String password;
    private String password2;
    @DateTimeFormat(pattern = "yyyy.MM.dd")   //todo some exception msg if wrong format
    private Date dateOfBirth;
    private boolean isAdmin;
}
