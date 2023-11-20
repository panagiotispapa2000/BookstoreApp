package com.bookstore.application.users;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class UserDTO {
    private Long id;
    private String username;
    private String email;
}