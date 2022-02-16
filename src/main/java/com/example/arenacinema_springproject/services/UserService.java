package com.example.arenacinema_springproject.services;
import com.example.arenacinema_springproject.models.entities.User;
import com.example.arenacinema_springproject.models.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.arenacinema_springproject.exceptions.BadRequestException;
import com.example.arenacinema_springproject.exceptions.UnauthorizedException;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User login(String email, String password){
        if (email == null || email.isBlank()){
            throw new BadRequestException("Email is mandatory!");
        }
        if (password == null || password .isBlank()){
            throw new BadRequestException("Password is mandatory!");
        }
        User u  = userRepository.findByEmailAndPassword(email, password);
        if (u==null){
            throw new UnauthorizedException("Wrong data!");
        }
        return u;
    }
}
