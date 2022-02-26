package com.example.arenacinema_springproject.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class UserEditDTO {

    private String firstName;
    private String secondName;
    private String lastName;
    private String gender;
    private Date dateOfBirth;
}
