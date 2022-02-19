package com.example.arenacinema_springproject.controllers;

import com.example.arenacinema_springproject.exceptions.BadRequestException;
import com.example.arenacinema_springproject.exceptions.ConstraintValidationException;
import com.example.arenacinema_springproject.models.dto.UserEditDTO;
import com.example.arenacinema_springproject.models.dto.UserRegisterDTO;
import com.example.arenacinema_springproject.models.dto.UserResponseDTO;
import com.example.arenacinema_springproject.models.entities.User;
import com.example.arenacinema_springproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import java.util.Date;
import java.util.List;

@RestController
@Validated
public class UserController extends BaseController{


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
        session.setAttribute(ADMIN,u.isAdmin());
        UserResponseDTO dto = modelMapper.map(u, UserResponseDTO.class);
        return dto;
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponseDTO> getById(@PathVariable int id){
        User u = userService.getById(id);
        UserResponseDTO dto = modelMapper.map(u, UserResponseDTO.class);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/users")
    public List getAll(HttpServletRequest request){
        validateLogin(request);
        adminLogin(request);
        return userService.getAllUsers();
    }
    @PostMapping("/logout")
    public void logOut(HttpServletRequest request){
        request.getSession().invalidate();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUserById(@PathVariable int id, HttpServletRequest request) {
        validateLogin(request);
        User u = userService.getById(id);
         userService.deleteUserById(u,id);
    }

    @PostMapping("/reg")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody UserRegisterDTO user, BindingResult result) {
        if (result.hasErrors()) {
            throw new ConstraintValidationException(result.toString());
        }

        User u = userService.register(user);
        UserResponseDTO dto = modelMapper.map(u, UserResponseDTO.class);
        return ResponseEntity.ok(dto);
    }



//TODO edit only some fields
    @PutMapping("/users")
    public ResponseEntity<UserResponseDTO> edit(@RequestBody UserEditDTO user, HttpServletRequest request) {
        validateLogin(request);
        User u = userService.edit(user);
        UserResponseDTO dto = modelMapper.map(u, UserResponseDTO.class);
        return ResponseEntity.ok(dto);
    }



}
