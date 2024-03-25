package com.cloudnativewebapp.webapp.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Table(name = "EmailVerification")
public class EmailVerification {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String userId;

    String email;

    String sent_timestamp;

    String verified_timestamp;

    String status;

    String link;
}
