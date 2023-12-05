package com.project.unicomer.service;

import com.project.unicomer.dto.ClientDTO;
import com.project.unicomer.dto.ClientInformationDTO;

import java.util.List;

public interface ClientService {
    public ClientDTO getClientByDni(String dni);
    public List<ClientInformationDTO> getClientsInformation(String dni);
}
