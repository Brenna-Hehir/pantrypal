package edu.uga.cs.pantrypal.controller;

import edu.uga.cs.pantrypal.model.User;
import edu.uga.cs.pantrypal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class UserAuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/signup")
    public String showSignupForm() {
        return "signup";
    }

    @PostMapping("/signup")
    public String registerUser(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return "Username already taken";
        }
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        userRepository.save(user);
        return "Registration successful";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody User user) {
        return userRepository.findByUsername(user.getUsername())
                .filter(dbUser -> passwordEncoder.matches(user.getPasswordHash(), dbUser.getPasswordHash()))
                .map(u -> "Login successful")
                .orElse("Invalid credentials");
    }
}
