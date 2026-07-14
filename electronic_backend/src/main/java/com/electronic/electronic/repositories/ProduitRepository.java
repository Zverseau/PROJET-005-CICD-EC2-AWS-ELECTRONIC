package com.electronic.electronic.repositories;

import com.electronic.electronic.models.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface ProduitRepository extends JpaRepository<Produit, UUID> {
    long countByActifTrueAndStockGreaterThan(int stock);

    long countByActifTrueAndStockLessThanEqual(int stock);
    List<Produit> findByActifTrue();
}
