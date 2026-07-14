package com.electronic.electronic.services.serviceImpl;

import com.electronic.electronic.DTOs.Response.DashboardStatResponseDTO;
import com.electronic.electronic.models.StatutCommande;
import com.electronic.electronic.models.StatutPaiement;
import com.electronic.electronic.repositories.CommandeRepository;
import com.electronic.electronic.repositories.PaiementRepository;
import com.electronic.electronic.repositories.ProduitRepository;
import com.electronic.electronic.services.StatistiqueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatistiqueServiceImpl implements StatistiqueService {

    private final PaiementRepository paiementRepository;
    private final CommandeRepository commandeRepository;
    private final ProduitRepository produitRepository;

    @Override
    public DashboardStatResponseDTO getDashboardStats() {

        DashboardStatResponseDTO stats = new DashboardStatResponseDTO();

        stats.setChiffreAffaireMensuel(getChiffreAffaireMensuel());
        stats.setPourcentageCommandesLivrees(getPourcentageCommandesLivrees());
        stats.setProduitsDisponibles(getNombreProduitsDisponibles());
        stats.setProduitsStockBas(getNombreProduitsStockBas());

        return stats;
    }

    @Override
    public Double getChiffreAffaireMensuel() {

        Double chiffreAffaire = paiementRepository.chiffreAffaireMensuel(StatutPaiement.VALIDE);

        return chiffreAffaire != null ? chiffreAffaire : 0.0;
    }

    @Override
    public Double getPourcentageCommandesLivrees() {

        long totalCommandes = commandeRepository.count();
        long commandesLivrees = commandeRepository.countByStatutCommande(StatutCommande.EXPEDIEE);

        if (totalCommandes == 0) {
            return 0.0;
        }

        return (commandesLivrees * 100.0) / totalCommandes;
    }

    @Override
    public Long getNombreProduitsDisponibles() {
        return produitRepository.countByActifTrueAndStockGreaterThan(0);
    }

    @Override
    public Long getNombreProduitsStockBas() {
        return produitRepository.countByActifTrueAndStockLessThanEqual(5);
    }

}