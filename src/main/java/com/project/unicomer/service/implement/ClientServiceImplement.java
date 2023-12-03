package com.project.unicomer.service.implement;

import com.project.unicomer.dto.ClientDTO;
import com.project.unicomer.model.Client;
import com.project.unicomer.repository.ClientRepository;
import com.project.unicomer.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImplement implements ClientService {
    @Autowired
    private ClientRepository clientRepository;
    @Override
    public ClientDTO getClientByDni(String dni) {
       return new ClientDTO(clientRepository.findByDni(dni).orElseThrow());
    }
}
