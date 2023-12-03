package com.project.unicomer.dto;

import com.project.unicomer.model.Client;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class ClientDTO {
    private String fullname;
    private String urlImg;
    private Set<CardDTO> cards;
    public ClientDTO(Client client){
        this.fullname = client.getFullName();
        this.urlImg = client.getUrlImg();
        this.cards = client.getCards()
                .stream()
                .map(CardDTO::new)
                .collect(Collectors.toSet());
    }
}
