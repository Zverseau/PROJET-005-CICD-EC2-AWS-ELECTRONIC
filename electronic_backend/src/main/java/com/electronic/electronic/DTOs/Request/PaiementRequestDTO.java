package com.electronic.electronic.DTOs.Request;

import com.electronic.electronic.models.StatutPaiement;

import lombok.*;

import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaiementRequestDTO {
    private String referencePaiement;
    private double montantTotal;
    private StatutPaiement statutPaiement;
    private UUID commandeId;
}
