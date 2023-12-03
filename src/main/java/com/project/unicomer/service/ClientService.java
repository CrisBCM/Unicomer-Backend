package com.project.unicomer.service;

import com.project.unicomer.dto.ClientDTO;

public interface ClientService {
    public ClientDTO getClientByDni(String dni);
}
