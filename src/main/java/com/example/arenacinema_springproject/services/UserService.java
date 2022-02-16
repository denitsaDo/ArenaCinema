package com.example.arenacinema_springproject.services;
import com.example.arenacinema_springproject.exceptions.NotFoundException;
import com.example.arenacinema_springproject.models.dto.UserResponseDTO;
import com.example.arenacinema_springproject.models.entities.User;
import com.example.arenacinema_springproject.models.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.arenacinema_springproject.exceptions.BadRequestException;
import com.example.arenacinema_springproject.exceptions.UnauthorizedException;

import java.util.Optional;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper mapper;

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

    public UserResponseDTO getById(int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User u = user.get();
            UserResponseDTO dto = mapper.map(u, UserResponseDTO.class);
            return dto;
        } else {
            throw new NotFoundException("User not found");
        }
    }
}
