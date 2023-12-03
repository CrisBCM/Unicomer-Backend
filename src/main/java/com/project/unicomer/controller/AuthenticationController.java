package com.project.unicomer.controller;

import com.project.unicomer.model.AuthenticationRequest;
import com.project.unicomer.model.AuthenticationResponse;
import com.project.unicomer.model.RegisterRequest;
import com.project.unicomer.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;
    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterRequest registerRequest){
        if(authenticationService.existsByDni(registerRequest.getDni()))
            return new ResponseEntity<>("El DNI ya se encuentra registrado", HttpStatus.BAD_REQUEST);

        return ResponseEntity.ok(authenticationService.register(registerRequest));
    }
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody AuthenticationRequest authenticationRequest){
        if(!authenticationService.existsByDni(authenticationRequest.getDni()))
            return new ResponseEntity<>("EL DNI NO se encuentra registrado", HttpStatus.BAD_REQUEST);

        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }
}
