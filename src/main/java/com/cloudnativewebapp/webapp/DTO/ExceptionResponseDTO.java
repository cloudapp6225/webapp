package com.cloudnativewebapp.webapp.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponseDTO {
    private HttpStatus httpStatus;
    private String message;
}
