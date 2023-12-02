package com.project.unicomer.config;

import com.project.unicomer.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
    private final ClientRepository clientRepository;

    @Bean
    public UserDetailsService userDetailsService(){
        return username -> clientRepository.findByDni(username)
                .orElseThrow(() -> new UsernameNotFoundException(("DNI no encontrado")));
    }
}
