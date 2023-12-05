package com.project.unicomer.service.implement;

import com.project.unicomer.config.JwtService;
import com.project.unicomer.model.*;
import com.project.unicomer.repository.ClientRepository;
import com.project.unicomer.service.AuthenticationService;
import com.project.unicomer.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class AuthenticationServiceImplement implements AuthenticationService {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CardService cardService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getDni(),
                        authRequest.getPassword()
                )
        );

        Client client = clientRepository.findByDni(authRequest.getDni()).orElseThrow();

        Map <String, Object> extraClaims = new HashMap<>();

        extraClaims.put("client_id", client.getDni());

        var jwtToken = jwtService.generateToken(extraClaims, client);

        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public AuthenticationResponse register(RegisterRequest registerRequest) {

        String fullname = registerRequest.getName() + " " + registerRequest.getLastname();

        LocalDate date = LocalDate.now().plusYears(5);

        String thruDate  = date.getMonthValue() + "/" + date.getYear();

        Card newCard = Card.builder()
                .number(cardService.getRandomNumberCard())
                .cardHolder(fullname)
                .balance(BigDecimal.ZERO)
                .thruDate(thruDate)
                .build();

        Client newClient = Client.builder()
                .dni(registerRequest.getDni())
                .email(registerRequest.getEmail())
                .urlImg("../../../assets/images/user-icon.png")
                .fullName(fullname)
                .cards(new HashSet<>())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .build();

        newClient.addCard(newCard);


        clientRepository.save(newClient);

        Map <String, Object> extraClaims = new HashMap<>();

        extraClaims.put("client_id", newClient.getDni());

        var jwtToken = jwtService.generateToken(extraClaims, newClient);

        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();

    }

    @Override
    public boolean existsByDni(String dni) {
        return clientRepository.existsByDni(dni);
    }

    @Override
    public boolean existsByEmail(String email) {
        return clientRepository.existsByEmail(email);
    }
}
