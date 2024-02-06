package com.cloudnativewebapp.webapp.Service;

import com.cloudnativewebapp.webapp.DTO.UserDTO;
import com.cloudnativewebapp.webapp.Entity.User;
import com.cloudnativewebapp.webapp.Exception.*;

public interface UserServiceInterface {
    public UserDTO createUser(User user) throws UserAlreadyExistsException, DatabaseException, InvalidEmailAddressException, InvalidUserInputException;

    public UserDTO getUser(String userId) throws UserNotFoundException;

    public UserDTO getUserByUserName(String userName) throws UserNotFoundException;

    public UserDTO updateUser(User user, String userName) throws UserNotFoundException, DatabaseException;
}
