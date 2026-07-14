package com.electronic.electronic.repositories;

import com.electronic.electronic.models.LigneCommande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface LigneCommandeRepository extends JpaRepository<LigneCommande, UUID> {
}
