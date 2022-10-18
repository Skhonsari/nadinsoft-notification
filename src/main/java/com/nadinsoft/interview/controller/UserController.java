package com.nadinsoft.interview.controller;

import com.nadinsoft.interview.dto.UserDTO;
import com.nadinsoft.interview.model.User;
import com.nadinsoft.interview.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @PostMapping
    public void createUser(@RequestBody UserDTO usernamePassword) {
        userRepository.save(new User(usernamePassword.getUsername(), passwordEncoder.encode(usernamePassword.getPassword())));
    }

    @GetMapping
    public List<UserDTO> getUsers() {
        return userRepository.findAll().stream()
                .map(UserDTO::load)
                .collect(Collectors.toList());
    }
}
