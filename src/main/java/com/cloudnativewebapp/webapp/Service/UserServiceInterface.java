package com.cloudnativewebapp.webapp.Service;

import com.cloudnativewebapp.webapp.DTO.UserDTO;
import com.cloudnativewebapp.webapp.Entity.User;
import com.cloudnativewebapp.webapp.Exception.DatabaseException;
import com.cloudnativewebapp.webapp.Exception.InvalidEmailAddressException;
import com.cloudnativewebapp.webapp.Exception.UserAlreadyExistsException;
import com.cloudnativewebapp.webapp.Exception.UserNotFoundException;

public interface UserServiceInterface {
    public UserDTO createUser(User user) throws UserAlreadyExistsException, DatabaseException, InvalidEmailAddressException;

    public UserDTO getUser(String userId) throws UserNotFoundException;

    public UserDTO getUserByUserName(String userName) throws UserNotFoundException;

    public UserDTO updateUser(User user, String userName) throws UserNotFoundException, DatabaseException;
}
