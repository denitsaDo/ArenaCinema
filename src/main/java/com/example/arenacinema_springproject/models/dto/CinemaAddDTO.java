package com.example.arenacinema_springproject.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class CinemaAddDTO {

    private String name;
    private int cityId;
    private String phoneNumber;
    private String address;
    private String email;
}
