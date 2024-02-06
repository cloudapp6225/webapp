package com.cloudnativewebapp.webapp.Controller;


import com.cloudnativewebapp.webapp.DTO.UserDTO;
import com.cloudnativewebapp.webapp.Entity.User;
import com.cloudnativewebapp.webapp.Exception.DatabaseException;
import com.cloudnativewebapp.webapp.Exception.InvalidEmailAddressException;
import com.cloudnativewebapp.webapp.Exception.UserAlreadyExistsException;
import com.cloudnativewebapp.webapp.Exception.UserNotFoundException;
import com.cloudnativewebapp.webapp.Service.UserServiceInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserServiceInterface userService;

    @PostMapping("/v1/user")
    public ResponseEntity<UserDTO> createUserRequest(@Valid @RequestBody User user) throws UserAlreadyExistsException, DatabaseException, InvalidEmailAddressException {
        UserDTO userDTO = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    }

    @GetMapping("/v1/user/self")
    public ResponseEntity<UserDTO> getUserRequest() throws UserNotFoundException {
        String userName = String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        UserDTO getUserFromDB = userService.getUserByUserName(userName);
        return ResponseEntity.status(HttpStatus.OK).body(getUserFromDB);
    }

    @PutMapping("/v1/user/self")
    public ResponseEntity<UserDTO> UpdateRequest(@RequestBody User user) throws UserNotFoundException, DatabaseException {
        String userName = String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if(user.getId() != null || user.getUsername() != null
                || user.getAccount_created() != null || user.getAccount_updated() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        UserDTO getUserFromDB = userService.updateUser(user, userName);
        return ResponseEntity.status(HttpStatus.OK).body(getUserFromDB);
    }

    @PatchMapping("/v1/user/self")
    public ResponseEntity<Void> patchRequest(@RequestBody(required = false) Object body) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
    }

    @DeleteMapping("/v1/user/self")
    public ResponseEntity<Void> deleteRequest() {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
    }

    @RequestMapping(path = "/v1/user/self", method = {RequestMethod.HEAD, RequestMethod.OPTIONS})
    public ResponseEntity<Void> headOptionsMapping() {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
    }
}
