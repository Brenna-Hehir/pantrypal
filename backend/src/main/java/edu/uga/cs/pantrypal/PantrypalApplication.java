package edu.uga.cs.pantrypal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import edu.uga.cs.pantrypal.repository.UserRepository;
import edu.uga.cs.pantrypal.model.User;



@SpringBootApplication
public class PantrypalApplication {

	public static void main(String[] args) {
		SpringApplication.run(PantrypalApplication.class, args);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
	}


	@Bean
	CommandLineRunner init(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			if (userRepository.findByUsername("testuser").isEmpty()) {
				User user = new User();
				user.setUsername("testuser");
				user.setEmail("test@example.com");
				user.setPasswordHash(passwordEncoder.encode("testpass")); // password: testpass
				userRepository.save(user);
			}
		};
	}
}
