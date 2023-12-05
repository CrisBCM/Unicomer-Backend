package com.project.unicomer.service.implement;

import com.project.unicomer.dto.ClientDTO;
import com.project.unicomer.dto.ClientInformationDTO;
import com.project.unicomer.model.Client;
import com.project.unicomer.repository.ClientRepository;
import com.project.unicomer.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImplement implements ClientService {
    @Autowired
    private ClientRepository clientRepository;
    @Override
    public ClientDTO getClientByDni(String dni) {
       return new ClientDTO(clientRepository.findByDni(dni).orElseThrow());
    }

    @Override
    public List<ClientInformationDTO> getClientsInformation(String dni) {
        List<Client> clients = clientRepository.findAll().stream().filter(client -> !(client.getDni().equals(dni))).toList();
        return clients.stream().map(ClientInformationDTO::new).collect(Collectors.toList());
    }
}
