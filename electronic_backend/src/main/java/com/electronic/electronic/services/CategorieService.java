package com.electronic.electronic.services;

import com.electronic.electronic.DTOs.Request.CategorieRequestDTO;
import com.electronic.electronic.DTOs.Response.CategorieResponseDTO;

import java.util.List;
import java.util.UUID;

public interface CategorieService {

    CategorieResponseDTO createCategorie(CategorieRequestDTO dto);

    CategorieResponseDTO getCategorieById(UUID id);

    List<CategorieResponseDTO> getAllCategories();

    CategorieResponseDTO updateCategorie(UUID id, CategorieRequestDTO dto);

    void deleteCategorie(UUID id);
}