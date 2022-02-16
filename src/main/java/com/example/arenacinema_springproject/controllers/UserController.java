package com.example.arenacinema_springproject.controllers;

import com.example.arenacinema_springproject.models.dto.UserResponseDTO;
import com.example.arenacinema_springproject.models.entities.User;
import com.example.arenacinema_springproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.modelmapper.ModelMapper;
import javax.servlet.http.HttpSession;

@RestController
public class UserController {

    public static final String LOGGED = "logged";
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/login")
    public UserResponseDTO login(@RequestBody User user, HttpSession session){
        if (session.getAttribute(LOGGED)!=null){
            System.out.println("You are already logged!");
        }
        String email = user.getEmail();
        String password = user.getPassword();
        User u = userService.login(email,password);
        session.setAttribute(LOGGED, true);
        UserResponseDTO dto = modelMapper.map(u, UserResponseDTO.class);
        return dto;
    }

}
