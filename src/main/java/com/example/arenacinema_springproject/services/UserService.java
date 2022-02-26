package com.example.arenacinema_springproject.services;
import com.example.arenacinema_springproject.exceptions.*;
import com.example.arenacinema_springproject.models.dto.*;
import com.example.arenacinema_springproject.models.entities.User;
import com.example.arenacinema_springproject.models.entities.UsersRateMovies;
import com.example.arenacinema_springproject.models.repositories.MovieRepository;
import com.example.arenacinema_springproject.models.repositories.UserRepository;
import com.example.arenacinema_springproject.models.repositories.UsersRateMoviesRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.arenacinema_springproject.controllers.BaseController.USER_ID;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UsersRateMoviesRepository usersRateMoviesRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static final int MAX_LENGTH = 30;


    public User login(String email, String password) {
        if (email == null || email.isBlank() ) {
            throw new BadRequestException("Email is mandatory!");
        }
        if (password == null || password.isBlank()) {
            throw new BadRequestException("Password is mandatory!");
        }
        User u = userRepository.findByEmail(email);
        if (u == null) {
            throw new NotFoundException("Wrong credentials.");
        } else {
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
        } else {
            throw new NotFoundException("User not found");
        }
    }

    public List<UserResponseDTO> getAllUsers() {
        List<UserResponseDTO> dto = userRepository.findAll().stream().map(user -> modelMapper.map(user, UserResponseDTO.class)).collect(Collectors.toList());
        return dto;
    }

    public void deleteUserById(User user) {
        Optional<User> optional = userRepository.findById(user.getId());   //check if id is >0
        if (optional.isPresent()) {
            userRepository.delete(user);
            throw new NoContentException();
        } else {
            throw new NotFoundException("No such user.");
        }
    }

    public User register(UserRegisterDTO user) {
        validateMandatoryFields(user);
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new BadRequestException("All fields are mandatory");
        }
        if (user.getEmail().length() > MAX_LENGTH){
            throw new BadRequestException("Email name is too long!");
        }
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new BadRequestException("User already exists!");
        }

        validateStrongPassword(user.getPassword());
        validateMatchingPasswords(user.getPassword(), user.getPassword2());

        User u = new User();
        u.setFirstName(user.getFirstName());
        u.setSecondName(user.getSecondName());
        u.setLastName(user.getLastName());
        u.setGender(user.getGender());
        u.setEmail(user.getEmail());
        u.setDateOfBirth(user.getDateOfBirth());
        u.setAdmin(false);
        u.setPassword(passwordEncoder.encode(user.getPassword()));  // bcrypt password
        userRepository.save(u);
        return u;
    }


    public User edit(UserEditDTO user) {
        Optional<User> opt = userRepository.findById(user.getId());
        if(opt.isPresent()){
            validateMandatoryFields(modelMapper.map(user, UserRegisterDTO.class));
            User u = opt.get();
            u.setFirstName(user.getFirstName());
            u.setSecondName(user.getSecondName());
            u.setLastName(user.getLastName());
            u.setGender(user.getGender());
            u.setDateOfBirth(user.getDateOfBirth());
            userRepository.save(u);
            return u;
        }
        else{
            throw new NotFoundException("User not found");
        }
    }




    public User editPassword(UserPasswordEditDTO user, int userId) {
        Optional<User> opt = userRepository.findById(userId);
        if(opt.isPresent()){
            User u = opt.get();

            if(!passwordEncoder.matches(user.getOldPassword(),u.getPassword())){
                throw new BadRequestException("Wrong password!");
            }
            validateMatchingPasswords(user.getNewPassword(), user.getNewPassword2());
            validateStrongPassword(user.getNewPassword());


            u.setPassword(passwordEncoder.encode(user.getNewPassword())); // bcrypt password
            userRepository.save(u);
            return u;
        }
        else{
            throw new NotFoundException("User not found");
        }
    }
    private void validateMandatoryFields(UserRegisterDTO user) {
        if (user.getFirstName() == null || user.getFirstName().isBlank() || user.getSecondName() == null || user.getSecondName().isBlank() || user.getLastName() == null || user.getLastName().isBlank() ||
                user.getGender() == null || user.getGender().isBlank()  || user.getDateOfBirth() == null) {
            throw new BadRequestException("All fields are mandatory");
        }
        if (user.getFirstName().length() > MAX_LENGTH){
            throw new BadRequestException("First name is too long!");
        }
        if (user.getSecondName().length() > MAX_LENGTH){
            throw new BadRequestException("Second name is too long!");
        }
        if (user.getLastName().length() > MAX_LENGTH){
            throw new BadRequestException("Last name is too long!");
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(user.getDateOfBirth());

        if (Calendar.getInstance().before(cal)) {
            throw new BadRequestException("Please enter a valid birth date.");
        }
        if ((Calendar.getInstance().get(Calendar.YEAR) - cal.get(Calendar.YEAR)) <16){
            throw new BadRequestException("You should be at least 16yo.");
        }
    }
    private void validateStrongPassword(String password) {
        if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
            throw new BadRequestException("Your password must be at least 8 symbols and have at least one lowercase letter , " +
                    "one uppercase letter, one digit and one special character");
        }
    }
    private void validateMatchingPasswords(String password, String password2) {
        if (!password.equals(password2)) {
            throw new BadRequestException("Passwords mismatch.");
        }
    }


    public void rateMovie(MovieRatingAddDTO movieRating, HttpServletRequest request) {
        int userId = (Integer) request.getSession().getAttribute(USER_ID);
        int movieId = movieRating.getMovieId();
        int rating = movieRating.getRating();
        if (userId ==0 || movieId ==0 || rating == 0) {
            throw new BadRequestException("All fields are mandatory!");
        }
        if (rating !=1 && rating !=2 && rating !=3 && rating!=4 && rating !=5) {
            throw new BadRequestException("Movie`s rating should be between 1 and 5 ");
        }
        if (usersRateMoviesRepository.findByUserRatesMovieIdAndMovieRatedByUserId(userId, movieId).isPresent()){
            throw new BadRequestException("You have already rated this movie.");
        }

        UsersRateMovies newRating = new UsersRateMovies();
        newRating.setUserRatesMovie(userRepository.findById(userId).orElseThrow(()-> new BadRequestException("No user with this id")));
        newRating.setMovieRatedByUser(movieRepository.findById(movieId).orElseThrow(()-> new BadRequestException("No movie with this movie id")));
        newRating.setRating(rating);
        usersRateMoviesRepository.save(newRating);
        throw new CreatedException("Rating added successfully");
    }
}
