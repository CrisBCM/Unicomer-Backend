package com.project.unicomer.controller;

import com.project.unicomer.dto.CardDTO;
import com.project.unicomer.dto.ClientDTO;
import com.project.unicomer.model.Card;
import com.project.unicomer.model.Client;
import com.project.unicomer.repository.CardRepository;
import com.project.unicomer.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/demo")
public class DemoController {
    @Autowired
    public CardRepository cardRepository;
    @GetMapping("/hola")
    public CardDTO demoHola(){
//       return clientRepository.findAll().stream().map(ClientDTO::new).collect(Collectors.toList());
        return new CardDTO(cardRepository.findByNumber("3523 4297 9590 6729"));
    }
    @GetMapping("/chau")
    public String demoChau(){
        return "CHAU";
    }
}
