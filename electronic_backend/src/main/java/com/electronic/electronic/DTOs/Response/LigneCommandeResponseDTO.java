package com.electronic.electronic.DTOs.Response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LigneCommandeResponseDTO {

    private ProduitResponseDTO produit;
    private double quantiteAchete;
    private double sousTotal;
}