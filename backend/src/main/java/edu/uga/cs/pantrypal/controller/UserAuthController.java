package edu.uga.cs.pantrypal.controller;

import edu.uga.cs.pantrypal.model.User;
import edu.uga.cs.pantrypal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // Allow frontend to access this
public class UserAuthController {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/signup")
    public String registerUser(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return "Username already taken";
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return "Email already in use";
        }
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        userRepository.save(user);
        return "Registration successful";
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody User loginData) {
        Optional<User> userOptional = userRepository.findByUsername(loginData.getUsername());
        if (userOptional.isEmpty()) {
            return "Invalid credentials";
        }
        User user = userOptional.get();
        if (passwordEncoder.matches(loginData.getPasswordHash(), user.getPasswordHash())) {
            return "Login successful";
        } else {
            return "Invalid credentials";
        }
    }
}
