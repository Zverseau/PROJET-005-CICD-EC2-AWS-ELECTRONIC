package com.electronic.electronic.services.serviceImpl;

import com.electronic.electronic.DTOs.Request.CategorieRequestDTO;
import com.electronic.electronic.DTOs.Response.CategorieResponseDTO;
import com.electronic.electronic.mappers.CategorieMapper;
import com.electronic.electronic.models.Categorie;
import com.electronic.electronic.models.Produit;
import com.electronic.electronic.repositories.CategorieRepository;
import com.electronic.electronic.services.CategorieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategorieServiceImpl implements CategorieService {

    private final CategorieRepository categorieRepository;
    private final CategorieMapper categorieMapper;


    @Override
    public CategorieResponseDTO createCategorie(CategorieRequestDTO dto) {

        Categorie categorie = categorieMapper.toEntity(dto);
        categorie.setActif(true);

        Categorie savedCategorie =
                categorieRepository.save(categorie);

        return categorieMapper.toResponse(savedCategorie);
    }


    @Override
    public CategorieResponseDTO getCategorieById(UUID id) {

        Categorie categorie = categorieRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Categorie introuvable"));

        return categorieMapper.toResponse(categorie);
    }

    @Override
    public List<CategorieResponseDTO> getAllCategories() {

        return categorieRepository.findByActifTrue()
                .stream()
                .map(categorieMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CategorieResponseDTO updateCategorie(UUID id,
                                                CategorieRequestDTO dto) {

        Categorie categorie = categorieRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Categorie introuvable"));

        categorie.setNomCategorie(dto.getNomCategorie());

        Categorie updatedCategorie =
                categorieRepository.save(categorie);

        return categorieMapper.toResponse(updatedCategorie);
    }


    @Override
    public void deleteCategorie(UUID id) {

        Categorie categorie = categorieRepository.findById(id)
                .orElseThrow();

        categorie.setActif(false);

        for(Produit p : categorie.getProduits()){
            p.setActif(false);
        }

        categorieRepository.save(categorie);
    }
}