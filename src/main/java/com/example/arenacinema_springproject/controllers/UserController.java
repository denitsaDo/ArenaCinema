package com.example.arenacinema_springproject.controllers;

import com.example.arenacinema_springproject.exceptions.UnauthorizedException;
import com.example.arenacinema_springproject.models.dto.UserRegisterDTO;
import com.example.arenacinema_springproject.models.dto.UserResponseDTO;
import com.example.arenacinema_springproject.models.entities.User;
import com.example.arenacinema_springproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@RestController
public class UserController {

    public static final String LOGGED = "logged";
    public static final String LOGGED_FROM = "logged_from";
    public static final String USER_ID = "user_id";
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;


    @PostMapping("/login")
    public UserResponseDTO login(@RequestBody User user, HttpSession session,  HttpServletRequest request){
        if (session.getAttribute(LOGGED)!=null){
            System.out.println("You are already logged!");
        }
        String email = user.getEmail();
        String password = user.getPassword();
        User u = userService.login(email,password);
        session.setAttribute(LOGGED, true);
        session.setAttribute(LOGGED_FROM, request.getRemoteAddr()); //chechk ip
        session.setAttribute(USER_ID, u.getId());
        UserResponseDTO dto = modelMapper.map(u, UserResponseDTO.class);
        return dto;
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponseDTO> getById(@PathVariable int id){
        User u = userService.getById(id);
        UserResponseDTO dto = modelMapper.map(u, UserResponseDTO.class);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/logout")
    public void logOut(HttpSession session){
        session.invalidate();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUserById(@PathVariable int id, HttpSession session, HttpServletRequest request) {
        validateLogin(session, request);
        User u = userService.getById(id);
        userService.deleteUserById(u,id);
    }

    @PostMapping("/reg")
    public ResponseEntity<UserResponseDTO> register(@RequestBody UserRegisterDTO user) {
        String firstName = user.getFirstName();
        String secondName = user.getSecondName();
        String lastName = user.getLastName();
        String gender = user.getGender();
        String email = user.getEmail();
        String password = user.getPassword();
        String confirmPassword = user.getPassword2();
        Date dateOfBirth = user.getDateOfBirth();
        boolean isAdmin = false;                //registered user is not admin by default
        User u = userService.register(firstName, secondName, lastName,
                gender, email, password,confirmPassword, dateOfBirth, isAdmin);
        UserResponseDTO dto = modelMapper.map(u, UserResponseDTO.class);
        return ResponseEntity.ok(dto);
    }

    private void validateLogin(HttpSession session, HttpServletRequest request) {
        if(session.isNew() ||
                (session.getAttribute(LOGGED)==null)||
                (!(Boolean) session.getAttribute(LOGGED)) ||
                (!request.getRemoteAddr().equals(session.getAttribute(LOGGED_FROM)))) {   //this checks IP and is used against hijacking
            throw new UnauthorizedException("You have to login.");
        }
    }

}
