package com.electronic.electronic.repositories;

import com.electronic.electronic.models.Commande;
import com.electronic.electronic.models.StatutCommande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface CommandeRepository extends JpaRepository<Commande, UUID> {
    List<Commande> findByStatutCommande(StatutCommande statutCommande);
    long countByStatutCommande(StatutCommande statutCommande);

    List<Commande> findByStatutCommandeOrderByCreateDateDesc(StatutCommande statut);
    List<Commande> findByStatutCommandeNotOrderByCreateDateDesc(StatutCommande statut);
}
