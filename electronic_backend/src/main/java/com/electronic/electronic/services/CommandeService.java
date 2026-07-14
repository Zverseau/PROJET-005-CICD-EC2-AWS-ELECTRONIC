package com.electronic.electronic.services;

import com.electronic.electronic.DTOs.Request.CategorieRequestDTO;
import com.electronic.electronic.DTOs.Request.CommandeRequestDTO;
import com.electronic.electronic.DTOs.Response.CategorieResponseDTO;
import com.electronic.electronic.DTOs.Response.CommandeResponseDTO;
import com.electronic.electronic.models.StatutCommande;

import java.util.List;
import java.util.UUID;

public interface CommandeService {

    CommandeResponseDTO passerCommande(CommandeRequestDTO request);
    List<CommandeResponseDTO> listeCommande();
    List<CommandeResponseDTO> listeCommandesEnAttente();
    CommandeResponseDTO updateStatutCommande(UUID id, StatutCommande nouveauStatut);
    CommandeResponseDTO getCommandeById(UUID id);

}