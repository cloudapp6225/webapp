package com.cloudnativewebapp.webapp.Controller;


import com.cloudnativewebapp.webapp.DTO.UserDTO;
import com.cloudnativewebapp.webapp.Entity.User;
import com.cloudnativewebapp.webapp.Exception.*;
import com.cloudnativewebapp.webapp.PubSub.PublishWithCustomAttributes;
import com.cloudnativewebapp.webapp.Service.UserServiceInterface;
import com.cloudnativewebapp.webapp.Service.VerificationServiceInterface;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private VerificationServiceInterface verificationServiceInterface;

    @Autowired
    HttpServletRequest request;
    HttpHeaders header;
    Logger logger = LoggerFactory.getLogger(UserController.class);

    PublishWithCustomAttributes publishWithCustomAttributes= null;

    @Value("${topic-name}")
    String topic_name;

    @Value("${environment}")
    String environment;


    public UserController() {
        header = new HttpHeaders();
        publishWithCustomAttributes = new PublishWithCustomAttributes();
        header.set("Cache-Control", "no-cache, no-store, must-revalidate;");
        header.set("Pragma", "no-cache");
        header.set("X-Content-Type-Options", "nosniff");
    }

    @PostMapping("/v2/user")
    public ResponseEntity<UserDTO> createUserRequest(@RequestBody User user) throws UserAlreadyExistsException, DatabaseException, InvalidEmailAddressException, InvalidUserInputException, InterruptedException {
        UserDTO userDTO = userService.createUser(user);
        System.out.println(topic_name);
        if(environment.equals("prod")) {
            publishWithCustomAttributes.publishData("dev-gcp-project-1", topic_name, userDTO);
        }
        verificationServiceInterface.saveEmaillink(userDTO.getUsername(), userDTO.getId());
        logger.info(String.valueOf(userDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    }

    @GetMapping("/v2/user/self")
    public ResponseEntity<UserDTO> getUserRequest(@RequestBody(required = false) Object body) throws UserNotFoundException {
        if(body != null || request.getQueryString() != null) {
            logger.error("Bad request");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .headers(header).build();
        }
        String userName = String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        String verifiedStatus = verificationServiceInterface.getVerificationStatus(userName);
        if(verifiedStatus.equals("success")) {
            UserDTO getUserFromDB = userService.getUserByUserName(userName);
            logger.info(String.valueOf(getUserFromDB));
            return ResponseEntity.status(HttpStatus.OK).body(getUserFromDB);
        }
        else {
            logger.error("User email " + userName + " is not authorized");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }

    @PutMapping("/v2/user/self")
    public ResponseEntity<UserDTO> UpdateRequest(@RequestBody User user) throws UserNotFoundException, DatabaseException {
        String userName = String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if(user.getId() != null || user.getUsername() != null
                || user.getAccount_created() != null || user.getAccount_updated() != null) {
            logger.error("Required fields are not passed to create the user");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        String verifiedStatus = verificationServiceInterface.getVerificationStatus(userName);
        if(verifiedStatus.equals("success")) {
            UserDTO getUserFromDB = userService.updateUser(user, userName);
            logger.info("User details updated successfully " + getUserFromDB.getUsername());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        else {
            logger.error("User email " + userName + " is not authorized");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }

    @PatchMapping("/v2/user/self")
    public ResponseEntity<Void> patchRequest(@RequestBody(required = false) Object body) {
        logger.warn("request is not allowed");
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
    }

    @DeleteMapping("/v2/user/self")
    public ResponseEntity<Void> deleteRequest() {
        logger.warn("request is not allowed");
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
    }

    @RequestMapping(path = "/v2/user/self", method = {RequestMethod.HEAD, RequestMethod.OPTIONS})
    public ResponseEntity<Void> headOptionsMapping() {
        logger.warn("request is not allowed");
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
    }
}
