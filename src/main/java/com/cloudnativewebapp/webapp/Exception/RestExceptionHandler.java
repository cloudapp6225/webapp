package com.cloudnativewebapp.webapp.Exception;

import com.cloudnativewebapp.webapp.DTO.ExceptionResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionResponseDTO> handleUserNotFoundException(UserNotFoundException exception) {
        ExceptionResponseDTO msg = new ExceptionResponseDTO(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponseDTO> handleUserAlreadyExistsException(UserAlreadyExistsException exception) {
        ExceptionResponseDTO msg = new ExceptionResponseDTO(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<ExceptionResponseDTO> handleDatabaseExceptions(DatabaseException exception) {
        ExceptionResponseDTO msg = new ExceptionResponseDTO(HttpStatus.SERVICE_UNAVAILABLE, exception.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(msg);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ExceptionResponseDTO> handleSpringSecurityException(UsernameNotFoundException ex) {
        ExceptionResponseDTO msg = new ExceptionResponseDTO(HttpStatus.NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
    }

    @ExceptionHandler(InvalidEmailAddressException.class)
    public ResponseEntity<ExceptionResponseDTO> handleInvalidEmailException(InvalidEmailAddressException ex) {
        ExceptionResponseDTO msg = new ExceptionResponseDTO(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
    }

    @ExceptionHandler(InvalidUserInputException.class)
    public ResponseEntity<ExceptionResponseDTO> handleInvalidInputException(InvalidUserInputException ex) {
        ExceptionResponseDTO msg = new ExceptionResponseDTO(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
    }


}
