package com.electronic.electronic.DTOs.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProduitRequestDTO {

    private String nomProduit;
    private String description;
    private int stock;
    private double prixUnitaire;
    private String photoProduit;
    private UUID categorieId;
}