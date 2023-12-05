package com.project.unicomer.controller;

import com.project.unicomer.dto.ClientDTO;
import com.project.unicomer.dto.ClientInformationDTO;
import com.project.unicomer.model.Client;
import com.project.unicomer.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    private ClientService clientService;
    @GetMapping("/client/{dni}")
    public ClientDTO getClientByDni(@PathVariable String dni)
    {
        return clientService.getClientByDni(dni);
    }
    @GetMapping("/clients-info/{dni}")
    public List<ClientInformationDTO> getClientsInformation(@PathVariable String dni){
        return clientService.getClientsInformation(dni);
    }
}
