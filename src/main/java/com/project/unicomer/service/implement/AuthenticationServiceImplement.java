package com.project.unicomer.service.implement;

import com.project.unicomer.model.*;
import com.project.unicomer.repository.ClientRepository;
import com.project.unicomer.service.AuthenticationService;
import com.project.unicomer.service.CardService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class AuthenticationServiceImplement implements AuthenticationService {
    private ClientRepository clientRepository;
    private CardService cardService;
    private PasswordEncoder passwordEncoder;

    public boolean existsByDni(String dni){
        return clientRepository.existsByDni(dni);
    }
    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authRequest) {
        return null;
    }

    @Override
    public AuthenticationResponse register(RegisterRequest registerRequest) {

        String fullname = registerRequest.getName() + " " + registerRequest.getLastname();

        Card newCard = Card.builder()
                .cardHolder(fullname)
                .balance(BigDecimal.ZERO)
                .number(cardService.getRandomNumberCard())
                .thruDate(LocalDateTime.now().plusYears(5))
                .build();

        Client newClient = Client.builder()
                .dni(registerRequest.getDni())
                .email(registerRequest.getEmail())
                .fullName(fullname)
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .build();


    }
}
