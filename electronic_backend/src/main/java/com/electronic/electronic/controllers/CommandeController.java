package com.electronic.electronic.controllers;

import com.electronic.electronic.DTOs.Request.CommandeRequestDTO;
import com.electronic.electronic.DTOs.Response.CommandeResponseDTO;
import com.electronic.electronic.models.StatutCommande;
import com.electronic.electronic.services.CommandeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/commandes")
@RequiredArgsConstructor
public class CommandeController {

    private final CommandeService commandeService;

    @PostMapping("/commander")
    public ResponseEntity<CommandeResponseDTO> passerCommande(@RequestBody CommandeRequestDTO request) {
        CommandeResponseDTO response = commandeService.passerCommande(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CommandeResponseDTO>> getAllCommandes() {
        return ResponseEntity.ok(commandeService.listeCommande());
    }

    @GetMapping("/en-attente")
    public ResponseEntity<List<CommandeResponseDTO>> getCommandesEnAttente() {
        return ResponseEntity.ok(commandeService.listeCommandesEnAttente());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommandeResponseDTO> updateStatutCommande(
            @PathVariable UUID id,
            @RequestParam StatutCommande statut) {
        return ResponseEntity.ok(commandeService.updateStatutCommande(id, statut));
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<CommandeResponseDTO> getCommandeById(@PathVariable UUID id) {
        return ResponseEntity.ok(commandeService.getCommandeById(id));
    }
}