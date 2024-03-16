package com.cloudnativewebapp.webapp.Service;

import com.cloudnativewebapp.webapp.DTO.UserDTO;
import com.cloudnativewebapp.webapp.Entity.User;
import com.cloudnativewebapp.webapp.Exception.*;
import com.cloudnativewebapp.webapp.Repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.exception.DataException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.cloudnativewebapp.webapp.SpringConfig.AppConfig.passwordEncoder;

@Service
public class UserService implements UserServiceInterface{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;
    private Logger logger = LogManager.getLogger(UserService.class);
    Map<String, Object> message;
    @Override
    public UserDTO createUser(User user) throws UserAlreadyExistsException, DatabaseException, InvalidEmailAddressException, InvalidUserInputException {
        message = new HashMap<>();
        if(user.getId() != null || user.getAccount_created() != null || user.getAccount_updated() != null) {
            throw new InvalidUserInputException("Invalid fields provided");
        }

        if(user.getUsername() == null
                || user.getFirst_name() == null
                || user.getLast_name() == null
                || user.getPassword() == null) {
            throw new InvalidUserInputException("The fields username, first_name, last_name, password are required");
        }

        if(userRepository.findByUsername(user.getUsername()) != null) {
            throw new UserAlreadyExistsException("User already exists");
        }

        if(!isValidEmail(user.getUsername())) {
            throw new InvalidEmailAddressException("Invalid Email Address");
        }

        User newUser = User.builder()
                .username(user.getUsername())
                .first_name(user.getFirst_name())
                .last_name(user.getLast_name())
                .password(passwordEncoder().encode(user.getPassword()))
                .account_created(String.valueOf(LocalDateTime.now()))
                .account_updated(String.valueOf(LocalDateTime.now()))
                .build();
           try {
               User savedUser = userRepository.save(newUser);
               message.put("username", savedUser.getUsername());
               message.put("first_name", savedUser.getFirst_name());
               message.put("last_name", savedUser.getLast_name());
               message.put("account_created", savedUser.getAccount_created());
               message.put("account_updated", savedUser.getAccount_updated());
               logger.info(message);
               return modelMapper.map(savedUser, UserDTO.class);
           }
           catch (Exception e) {
               throw new DatabaseException(e.getMessage());
           }
    }

    @Override
    public UserDTO getUser(String userId) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(userId);
        if(!user.isPresent()) {
            throw new UserNotFoundException("User does not exist in database");
        }
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        return userDTO;
    }

    @Override
    public UserDTO getUserByUserName(String userName) throws UserNotFoundException {
        User user = userRepository.findByUsername(userName);
        if(user != null) {
            return modelMapper.map(user, UserDTO.class);
        }
        else {
            throw new UserNotFoundException("User does not exist in database");
        }
    }

    @Override
    public UserDTO updateUser(User updateUser, String userName) throws UserNotFoundException, DatabaseException {
        User getUserFromDB = userRepository.findByUsername(userName);
        if(getUserFromDB != null) {
            if(updateUser.getFirst_name() != null)
                getUserFromDB.setFirst_name(updateUser.getFirst_name());
            if(updateUser.getLast_name() != null)
                getUserFromDB.setLast_name(updateUser.getLast_name());
            if(updateUser.getPassword() != null)
                getUserFromDB.setPassword(passwordEncoder().encode(updateUser.getPassword()));

            getUserFromDB.setAccount_updated(String.valueOf(LocalDateTime.now()));
            try {
                User updatedUser = userRepository.save(getUserFromDB);
                return modelMapper.map(updatedUser, UserDTO.class);
            }
            catch (Exception e) {
                throw new DatabaseException(e.getMessage());
            }
        }
        else {
            throw new UserNotFoundException("The User does not exist in database");
        }
    }

    public boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(regex);
    }

}
