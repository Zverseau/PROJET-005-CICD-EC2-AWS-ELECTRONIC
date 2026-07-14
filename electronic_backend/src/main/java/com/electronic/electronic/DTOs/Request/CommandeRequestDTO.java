package com.electronic.electronic.DTOs.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommandeRequestDTO {

    private String nomClient;
    private String prenomClient;
    private String adresseClient;
    private String telephoneClient;

    private List<LigneCommandeRequestDTO> lignesCommande;
}