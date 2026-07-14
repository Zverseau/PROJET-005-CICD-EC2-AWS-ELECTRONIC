package com.electronic.electronic.DTOs.Response;

import com.electronic.electronic.models.StatutPaiement;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaiementResponseDTO {
    private UUID id;
    private String referencePaiement;
    private double montantTotal;
    private StatutPaiement statutPaiement;
    private UUID commandeId;
    private LocalDateTime createdAt;
}
