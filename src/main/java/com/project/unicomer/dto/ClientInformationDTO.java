package com.project.unicomer.dto;

import com.project.unicomer.model.Client;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ClientInformationDTO {
    private String name;
    private String email;
    private String urlImg;
    private List<String> numberCards = new ArrayList<>();

    public ClientInformationDTO (Client client){
        name = client.getFullName();
        email = client.getEmail();
        urlImg = client.getUrlImg();
        client.getCards().forEach(card -> numberCards.add(card.getNumber()));
    }
}
