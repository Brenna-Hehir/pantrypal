package edu.uga.cs.pantrypal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors()
            .and()
            .csrf().disable() // disable CSRF for now (only safe if no sessions or cookies)
            .authorizeHttpRequests()
                .requestMatchers("/api/**").permitAll() // allow unauthenticated access to /api
                .anyRequest().authenticated(); // require auth elsewhere (if needed)

        return http.build();
    }
}
