package com.poyecto.ticketerra_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @SuppressWarnings("removal")
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()  
            .authorizeHttpRequests()
                .requestMatchers("/api/usuarios/**").permitAll()  
                .anyRequest().authenticated()
            .and()
            .formLogin()
                .permitAll();

        return http.build();
    }
}