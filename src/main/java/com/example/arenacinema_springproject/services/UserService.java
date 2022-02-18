package com.example.arenacinema_springproject.services;
import com.example.arenacinema_springproject.exceptions.NoContentException;
import com.example.arenacinema_springproject.exceptions.NotFoundException;
import com.example.arenacinema_springproject.models.dto.UserEditDTO;
import com.example.arenacinema_springproject.models.dto.UserResponseDTO;
import com.example.arenacinema_springproject.models.entities.City;
import com.example.arenacinema_springproject.models.entities.User;
import com.example.arenacinema_springproject.models.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.arenacinema_springproject.exceptions.BadRequestException;
import com.example.arenacinema_springproject.exceptions.UnauthorizedException;

import java.time.LocalDate;
import java.time.Year;
import java.util.Date;
import java.util.List;
import java.util.Optional;


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
        User u  = userRepository.findByEmail(email);
        if (u == null) {
            throw new NotFoundException("Wrong credentials.");
        }
        else {
            if (!passwordEncoder.matches(password, u.getPassword())) {
                throw new UnauthorizedException("Wrong credentials.");
            }
            return u;
        }
    }

    public User getById(int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        }
        else {
            throw new NotFoundException("User not found");
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUserById(User user, int id) {
        Optional<User> optional = userRepository.findById(user.getId());   //check if id is >0
        if (optional.isPresent() ) {
            userRepository.delete(user);
            throw new NoContentException();
        }
        else {
            throw new NotFoundException("No such user.");
        }
    }

    public User register(String firstName, String secondName, String lastName, String gender,
                         String email, String password, String confirmPassword, Date dateOfBirth, boolean isAdmin) {
        if (firstName==null || firstName.isBlank() || secondName==null || secondName.isBlank() || lastName==null || lastName.isBlank() ||
            gender==null || gender.isBlank() || email==null || email.isBlank() || dateOfBirth==null || email.isBlank()) {
            throw new BadRequestException("All fields are mandatory");
        }

//        if (dateOfBirth.compareTo(LocalDate.now().) < LocalDate.now().va && now.getFullYear() - date.getFullYear() < 120) {                       //TODO check if date is before local date time
//            throw new BadRequestException("Date of birth is incorrect");
//        }

        if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
            throw new BadRequestException("Your password must be at least 8 symbols and have at least one lowercase letter , " +
                    "one uppercase letter, one digit and one special character");
        }

        if (!password.equals(confirmPassword)) {
            throw new BadRequestException("Passwords mismatch.");
        }
        if (userRepository.findByEmail(email) != null) {
            throw new BadRequestException("User already exists!");
        }

        User u = new User();
        u.setFirstName(firstName);
        u.setSecondName(secondName);
        u.setLastName(lastName);
        u.setGender(gender);
        u.setEmail(email);
        u.setDateOfBirth(dateOfBirth);
        u.setAdmin(isAdmin);
        u.setPassword(passwordEncoder.encode(password));  // bcrypt password
        userRepository.save(u);
        return u;
    }


    public User edit(UserEditDTO userEdited) {
        Optional<User> opt = userRepository.findById(userEdited.getId());
        if(opt.isPresent()){
            User u = opt.get();
            u.setFirstName(userEdited.getFirstName());
            u.setSecondName(userEdited.getSecondName());
            u.setLastName(userEdited.getLastName());
            u.setGender(userEdited.getGender());

            u.setDateOfBirth(userEdited.getDateOfBirth());
            userRepository.save(u);
            return u;
        }
        else{
            throw new NotFoundException("User not found");
        }

    }
}
