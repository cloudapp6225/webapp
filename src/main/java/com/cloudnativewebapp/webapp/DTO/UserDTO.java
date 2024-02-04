package com.cloudnativewebapp.webapp.DTO;

import lombok.Builder;
import lombok.Data;

@Data
public class UserDTO {
    private String id;
    private String first_name;
    private String last_name;
    private String username;
    private String account_created;
    private String account_updated;
}
