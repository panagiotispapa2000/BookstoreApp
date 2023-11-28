package com.bookstore.application.users;

import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.*;
import jakarta.validation.constraints.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Valid
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional(readOnly = true)
    public List<UserDTO> getUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public ResponseEntity<?> getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            UserDTO userDTO = modelMapper.map(user.get(), UserDTO.class);
            return ResponseEntity.status(HttpStatus.OK).body(userDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with ID " + id + " is not found");
        }
    }

    @Transactional
    public User create(@NotNull User user) {
        if (userRepository.findUserByEmail(user.getEmail()) != null) {
            throw new UserGlobalException("The provided email already exists for another user");
        } else if (userRepository.findUserByUsername(user.getUsername()) != null) {
            throw new UserGlobalException("The provided username already exists for another user");
        } else return userRepository.save(user);
    }
}
