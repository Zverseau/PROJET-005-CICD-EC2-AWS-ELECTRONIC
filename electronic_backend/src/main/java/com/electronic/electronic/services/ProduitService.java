package com.electronic.electronic.services;

import com.electronic.electronic.DTOs.Request.ProduitRequestDTO;
import com.electronic.electronic.DTOs.Response.ProduitResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface ProduitService {

    ProduitResponseDTO createProduit(ProduitRequestDTO dto, MultipartFile imageFile);

    ProduitResponseDTO getProduitById(UUID id);


    List<ProduitResponseDTO> getAllProduits();

    ProduitResponseDTO updateProduit(UUID id, ProduitRequestDTO dto,  MultipartFile imageFile);

    void deleteProduit(UUID id);
}