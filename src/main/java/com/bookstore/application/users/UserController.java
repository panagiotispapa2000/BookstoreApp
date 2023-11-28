package com.bookstore.application.users;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(path = "/users")
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getUsers();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getUserWithoutPass(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public ResponseEntity<?> registration(@Valid @RequestBody User user) {
        try {
            User newUser = userService.create(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        } catch (UserGlobalException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
