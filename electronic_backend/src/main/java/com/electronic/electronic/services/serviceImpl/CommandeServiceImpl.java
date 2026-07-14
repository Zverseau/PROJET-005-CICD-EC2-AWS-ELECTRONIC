package com.electronic.electronic.services.serviceImpl;

import com.electronic.electronic.DTOs.Request.CommandeRequestDTO;
import com.electronic.electronic.DTOs.Request.LigneCommandeRequestDTO;
import com.electronic.electronic.DTOs.Response.CommandeResponseDTO;
import com.electronic.electronic.mappers.CommandeMapper;
import com.electronic.electronic.models.*;
import com.electronic.electronic.repositories.CommandeRepository;
import com.electronic.electronic.repositories.PaiementRepository;
import com.electronic.electronic.repositories.ProduitRepository;
import com.electronic.electronic.services.CommandeService;
import com.electronic.electronic.utils.ReferenceGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CommandeServiceImpl implements CommandeService {

    private final CommandeRepository commandeRepository;
    private final ProduitRepository produitRepository;
    private final CommandeMapper commandeMapper;
    private final PaiementRepository paiementRepository;


    @Override
    public CommandeResponseDTO passerCommande(CommandeRequestDTO request) {
        // 1. Création de l'entité Commande de base
        Commande commande = new Commande();
        commande.setNomClient(request.getNomClient());
        commande.setPrenomClient(request.getPrenomClient());
        commande.setAdresseClient(request.getAdresseClient());
        commande.setTelephoneClient(request.getTelephoneClient());
        commande.setStatutCommande(StatutCommande.EN_ATTENTE); // Statut initial

        List<LigneCommande> lignes = new ArrayList<>();
        double montantTotal = 0.0;

        // 2. Traitement des lignes de commande et mise à jour du stock
        for (LigneCommandeRequestDTO item : request.getLignesCommande()) {
            Produit produit = produitRepository.findById(item.getProduitId())
                    .orElseThrow(() -> new RuntimeException("Produit non trouvé : " + item.getProduitId()));

            // Vérification du stock
            if (produit.getStock() < item.getQuantiteAchete()) {
                throw new RuntimeException("Stock insuffisant pour le produit : " + produit.getNomProduit());
            }

            // Mise à jour du stock
            produit.setStock(produit.getStock() - (int) item.getQuantiteAchete());
            produitRepository.save(produit);

            // Calcul du sous-total pour cette ligne
            double sousTotal = item.getQuantiteAchete() * produit.getPrixUnitaire();
            montantTotal += sousTotal;

            // Création de la ligne
            LigneCommande ligne = new LigneCommande();
            ligne.setProduit(produit);
            ligne.setQuantiteAchete(item.getQuantiteAchete());
            ligne.setCommande(commande);
            ligne.setSousTotal(sousTotal);
            lignes.add(ligne);
        }

        commande.setLignesCommande(lignes);

        // 3. Création du Paiement avec référence unique
        Paiement paiement = new Paiement();
        paiement.setCommande(commande);
        paiement.setMontantTotal(montantTotal);

        // Génération d'une référence unique à 6 chiffres
        String reference = ReferenceGenerator.genererReferenceUnique(paiementRepository);
        paiement.setReferencePaiement(reference);
        paiement.setStatutPaiement(StatutPaiement.VALIDE);

        commande.setPaiement(paiement);
       // commande.setStatutCommande(StatutCommande.EN_ATTENTE);

        // 4. Sauvegarde finale
        Commande savedCommande = commandeRepository.save(commande);

        return commandeMapper.toResponse(savedCommande);
    }

    @Override
    public List<CommandeResponseDTO> listeCommande() {
        List<Commande> commandesEnAttente = commandeRepository.findByStatutCommandeOrderByCreateDateDesc(StatutCommande.EN_ATTENTE);
        List<Commande> autresCommandes = commandeRepository.findByStatutCommandeNotOrderByCreateDateDesc(StatutCommande.EN_ATTENTE);

        List<Commande> toutes = new ArrayList<>();
        toutes.addAll(commandesEnAttente);
        toutes.addAll(autresCommandes);

        return toutes.stream()
                .map(commandeMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommandeResponseDTO> listeCommandesEnAttente() {
        return commandeRepository.findByStatutCommande(StatutCommande.EN_ATTENTE).stream()
                .map(commandeMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CommandeResponseDTO updateStatutCommande(UUID id, StatutCommande nouveauStatut) {
        Commande commande = commandeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée avec l'ID : " + id));
        commande.setStatutCommande(nouveauStatut);
        Commande updatedCommande = commandeRepository.save(commande);
        return commandeMapper.toResponse(updatedCommande);
    }

    @Override
    public CommandeResponseDTO getCommandeById(UUID id) {
        Commande commande = commandeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée avec l'id : " + id));

        return commandeMapper.toResponse(commande); 
    }


}
