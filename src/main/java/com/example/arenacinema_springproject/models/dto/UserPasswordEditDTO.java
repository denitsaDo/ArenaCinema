package com.example.arenacinema_springproject.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class UserPasswordEditDTO {
    private int id;
    private String oldPassword;
    private String newPassword;
    private String newPassword2;
}
