package com.amir.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize 
                .requestMatchers("/", "/error", "/login/**", "/oauth2/**").permitAll()
                .anyRequest().authenticated()
            )
            // enable Oauth 2.0 login 
            .oauth2Login(withDefaults());
        
        return http.build();
    }
}
