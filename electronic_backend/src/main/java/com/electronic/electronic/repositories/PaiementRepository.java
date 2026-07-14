package com.electronic.electronic.repositories;

import com.electronic.electronic.models.Paiement;
import com.electronic.electronic.models.StatutPaiement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface PaiementRepository extends JpaRepository<Paiement, UUID> {
    boolean existsByReferencePaiement(String reference);
    Optional<Paiement> findByReferencePaiement(String reference);

    @Query("""
SELECT SUM(p.montantTotal)
FROM Paiement p
WHERE MONTH(p.createDate) = MONTH(CURRENT_DATE)
AND YEAR(p.createDate) = YEAR(CURRENT_DATE)
AND p.statutPaiement = :statut
""")
    Double chiffreAffaireMensuel(@Param("statut") StatutPaiement statut);
}
