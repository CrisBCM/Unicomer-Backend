package com.project.unicomer.service;

import com.project.unicomer.model.AuthenticationRequest;
import com.project.unicomer.model.AuthenticationResponse;
import com.project.unicomer.model.RegisterRequest;

public interface AuthenticationService {
    public AuthenticationResponse authenticate(AuthenticationRequest authRequest);
    public AuthenticationResponse register(RegisterRequest registerRequest);

    public boolean existsByDni(String dni);
    public boolean existsByEmail(String email);
}
