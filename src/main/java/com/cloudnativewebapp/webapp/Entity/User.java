package com.cloudnativewebapp.webapp.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotBlank(message = "Please provide a valid first_name")
    private String first_name;

    @NotBlank(message = "Please provide a valid last_name")
    private String last_name;

    @NotBlank(message = "Please provide a password")
    private String password;

    private String username;

    private String account_created;
    private String account_updated;
}
