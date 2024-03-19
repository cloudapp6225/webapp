package com.cloudnativewebapp.webapp.Controller;


import com.cloudnativewebapp.webapp.DTO.UserDTO;
import com.cloudnativewebapp.webapp.Entity.User;
import com.cloudnativewebapp.webapp.Exception.*;
import com.cloudnativewebapp.webapp.Service.UserServiceInterface;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserServiceInterface userService;

    @Autowired
    HttpServletRequest request;
    HttpHeaders header;

    Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController() {
        header = new HttpHeaders();
        header.set("Cache-Control", "no-cache, no-store, must-revalidate;");
        header.set("Pragma", "no-cache");
        header.set("X-Content-Type-Options", "nosniff");
    }

    @PostMapping("/v1/user")
    public ResponseEntity<UserDTO> createUserRequest(@RequestBody User user) throws UserAlreadyExistsException, DatabaseException, InvalidEmailAddressException, InvalidUserInputException {
        UserDTO userDTO = userService.createUser(user);
        logger.info(String.valueOf(userDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    }

    @GetMapping("/v1/user/self")
    public ResponseEntity<UserDTO> getUserRequest(@RequestBody(required = false) Object body) throws UserNotFoundException {
        if(body != null || request.getQueryString() != null) {
            logger.error("Bad request");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .headers(header).build();
        }
        String userName = String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        UserDTO getUserFromDB = userService.getUserByUserName(userName);
        logger.info(String.valueOf(getUserFromDB));
        return ResponseEntity.status(HttpStatus.OK).body(getUserFromDB);
    }

    @PutMapping("/v1/user/self")
    public ResponseEntity<UserDTO> UpdateRequest(@RequestBody User user) throws UserNotFoundException, DatabaseException {
        String userName = String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if(user.getId() != null || user.getUsername() != null
                || user.getAccount_created() != null || user.getAccount_updated() != null) {
            logger.error("Required fields are not passed to create the user");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        UserDTO getUserFromDB = userService.updateUser(user, userName);
        logger.info("User details updated successfully " + getUserFromDB.getUsername());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/v1/user/self")
    public ResponseEntity<Void> patchRequest(@RequestBody(required = false) Object body) {
        logger.warn("request is not allowed");
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
    }

    @DeleteMapping("/v1/user/self")
    public ResponseEntity<Void> deleteRequest() {
        logger.warn("request is not allowed");
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
    }

    @RequestMapping(path = "/v1/user/self", method = {RequestMethod.HEAD, RequestMethod.OPTIONS})
    public ResponseEntity<Void> headOptionsMapping() {
        logger.warn("request is not allowed");
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
    }
}
