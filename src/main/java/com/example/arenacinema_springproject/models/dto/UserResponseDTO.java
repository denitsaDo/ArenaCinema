package com.example.arenacinema_springproject.models.dto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserResponseDTO {

    private int id;
    private String firstName;
    private String lastName;

}
