package com.example.arenacinema_springproject.controllers;

import com.example.arenacinema_springproject.models.dto.UserResponseDTO;
import com.example.arenacinema_springproject.models.entities.User;
import com.example.arenacinema_springproject.models.repositories.UserRepository;
import com.example.arenacinema_springproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper;
import javax.servlet.http.HttpSession;

@RestController
public class UserController {

    public static final String LOGGED = "logged";
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;

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
    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponseDTO> getById(@PathVariable int id){
        return ResponseEntity.ok(userService.getById(id));
    }

    @PostMapping("/logout")
    public void logOut(HttpSession session){
        session.invalidate();
    }
    @DeleteMapping("/users/{id}")
    public void deleteUserById(@PathVariable int id) {
        userRepository.deleteUserById(id);
    }
    /* public User register(UserRegistrationDTO u) {
        if(userRepository.findByEmail(u.getEmail())!=null){
            throw new BadRequestException("User with this email exists");
        }
        if(!u.getPassword().equals(u.getConfPassword())){
            throw new BadRequestException("Password mismatch");
        }
        User user = new User();
        user.setFirstName(u.getFirstName());
        user.setSecondName(u.getSecondName());
        user.setLastName(u.getLastName());
        user,setGender(u.getGender());
        user.setEmail(u.getEmail());
        user.setPassword(passwordEncoder.encode(u.getPassword()));
        user.setDateOfBirth(u.getDateOfBirth());
        userRepository.save(u);
        return user;
    }*/

}
