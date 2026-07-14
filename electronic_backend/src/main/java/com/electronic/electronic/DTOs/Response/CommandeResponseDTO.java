package com.electronic.electronic.DTOs.Response;

import com.electronic.electronic.models.StatutCommande;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommandeResponseDTO {

    private UUID id;

    private String nomClient;
    private String prenomClient;
    private String adresseClient;
    private String telephoneClient;
    private StatutCommande statutCommande;

    private List<LigneCommandeResponseDTO> lignesCommande;

    private PaiementResponseDTO paiement;
}