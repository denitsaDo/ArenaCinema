package com.example.arenacinema_springproject.controllers;

import com.example.arenacinema_springproject.exceptions.UnauthorizedException;
import com.example.arenacinema_springproject.models.dto.UserEditDTO;
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
import java.util.List;

@RestController
public class UserController {

    public static final String LOGGED = "logged";
    public static final String LOGGED_FROM = "logged_from";
    public static final String USER_ID = "user_id";
    public static final String ADMIN = "admin";
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
    public ResponseEntity<UserResponseDTO> register(@RequestBody UserRegisterDTO user) {
        String firstName = user.getFirstName();
        String secondName = user.getSecondName();
        String lastName = user.getLastName();
        String gender = user.getGender();
        String email = user.getEmail();
        String password = user.getPassword();
        String confirmPassword = user.getPassword2();
        Date dateOfBirth = user.getDateOfBirth();
        boolean isAdmin = Boolean.parseBoolean(user.getIsAdmin());                //registered user is not admin by default
        User u = userService.register(firstName, secondName, lastName,
                gender, email, password,confirmPassword, dateOfBirth, isAdmin);
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

    private void validateLogin(HttpServletRequest request) {
        HttpSession session = request.getSession();
        boolean isNewSession = session.isNew();
        boolean isLogged = session.getAttribute(LOGGED)!=null && ((Boolean)session.getAttribute(LOGGED));
        boolean isSameIP = request.getRemoteAddr().equals(session.getAttribute(LOGGED_FROM)); //this checks IP and is used against hijacking
        if(isNewSession || !isLogged || !isSameIP ) {
            throw new UnauthorizedException("You have to login.");
        }
    }

    private void adminLogin(HttpServletRequest request) {
        HttpSession session = request.getSession();
        boolean isAdminSession = (Boolean) session.getAttribute(ADMIN);
        if(!isAdminSession) {
            throw new UnauthorizedException("You should have admin rights.");
        }
    }

}
