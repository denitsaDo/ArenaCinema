package com.example.arenacinema_springproject.controllers;


import com.example.arenacinema_springproject.exceptions.BadRequestException;
import com.example.arenacinema_springproject.exceptions.ConstraintValidationException;
import com.example.arenacinema_springproject.exceptions.CreatedException;
import com.example.arenacinema_springproject.models.dto.*;
import com.example.arenacinema_springproject.models.entities.User;
import com.example.arenacinema_springproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
public class UserController extends BaseController{


    @Autowired
    private UserService userService;
    @Autowired
    private TicketController ticketController;



    @PostMapping("/reg")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody UserRegisterDTO user, BindingResult result, HttpServletRequest request) {
        if (request.getSession().getAttribute(LOGGED)!=null){
            throw new BadRequestException("You are already logged!");
        }
        if (result.hasErrors()) {
            throw new ConstraintValidationException(result.toString());
        }
        UserResponseDTO u = userService.register(user);
        return ResponseEntity.ok(u);
    }

    @PostMapping("/login")
    public UserResponseDTO login(@RequestBody User user, HttpServletRequest request){
        if (request.getSession().getAttribute(LOGGED)!=null){
            throw new BadRequestException("You are already logged!");
        }
        UserResponseDTO u = userService.login(user, request);
        return u;
    }

    @PutMapping("/users")
    public ResponseEntity<UserResponseDTO> edit(@RequestBody UserEditDTO user, HttpServletRequest request) {
        validateLogin(request);
        UserResponseDTO dto = userService.edit(user, request);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/users/changePassword")
    public ResponseEntity<UserResponseDTO> editPassword(@RequestBody UserPasswordEditDTO user, HttpServletRequest request) {
        validateLogin(request);
        UserResponseDTO dto = userService.editPassword(user, request);;
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/logout")
    public void logOut(HttpServletRequest request){
        request.getSession().invalidate();
    }

    @DeleteMapping("/users")
    public void delete(HttpServletRequest request) {
        validateLogin(request);
        userService.deleteUser(request);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponseDTO> getById(@PathVariable int id, HttpServletRequest request){
        validateLogin(request);
        adminLogin(request);
        UserResponseDTO u = userService.getById(id);
        return ResponseEntity.ok(u);
    }

    @GetMapping("/users")
    public List<UserResponseDTO> getAll(HttpServletRequest request){
        validateLogin(request);
        adminLogin(request);
        return userService.getAllUsers();
    }

    @PostMapping("/users/ticket")
    public void buyTicket(@RequestBody TicketAddDTO ticket, HttpServletRequest request){
        validateLogin(request);
        ticketController.add(ticket, request);
        throw new CreatedException("Ticket added.");
    }

    @PostMapping("/users/ratings")
    public void rateMovie(@RequestBody MovieRatingAddDTO movieRating, HttpServletRequest request){
        validateLogin(request);
        userService.rateMovie(movieRating, request);
    }
}
