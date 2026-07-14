package com.electronic.electronic.DTOs.Response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProduitResponseDTO {

    private UUID id;
    //private UUID categorieId;
    private String nomProduit;
    private double prixUnitaire;
    private String description;
    private int stock;
    private String photoProduit;
}