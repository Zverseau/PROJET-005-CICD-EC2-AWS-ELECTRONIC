package com.electronic.electronic.services.serviceImpl;

import com.electronic.electronic.DTOs.Request.ProduitRequestDTO;
import com.electronic.electronic.DTOs.Response.ProduitResponseDTO;
import com.electronic.electronic.mappers.ProduitMapper;
import com.electronic.electronic.models.Categorie;
import com.electronic.electronic.models.Produit;
import com.electronic.electronic.repositories.CategorieRepository;
import com.electronic.electronic.repositories.ProduitRepository;
import com.electronic.electronic.services.ProduitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProduitServiceImpl implements ProduitService {

    private final ProduitRepository produitRepository;
    private final CategorieRepository categorieRepository;
    private final ProduitMapper produitMapper;

    @Override
    public ProduitResponseDTO createProduit(ProduitRequestDTO dto, MultipartFile imageFile) {
        Produit produit = produitMapper.toEntity(dto);
        produit.setActif(true);

        if (imageFile != null && !imageFile.isEmpty()) {
            String fileName = saveImage(imageFile);
            produit.setPhotoProduit(fileName);
        }
        Categorie categorie = categorieRepository.findById(dto.getCategorieId())
                .orElseThrow(() -> new RuntimeException("Categorie introuvable"));
        produit.setCategorie(categorie);

        Produit savedProduit = produitRepository.save(produit);
        return produitMapper.toResponse(savedProduit);
    }

    // Méthode pour sauvegarder le fichier sur le serveur
    private String saveImage(MultipartFile file) {
        try {
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path uploadPath = Paths.get("uploads/produits/");

            if (!Files.exists(uploadPath)) Files.createDirectories(uploadPath);

            Files.copy(file.getInputStream(), uploadPath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de l'enregistrement de l'image", e);
        }
    }


    @Override
    public ProduitResponseDTO getProduitById(UUID id) {

        Produit produit = produitRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Produit introuvable"));

        return produitMapper.toResponse(produit);
    }

    @Override
    public List<ProduitResponseDTO> getAllProduits() {

        return produitRepository.findByActifTrue()
                .stream()
                .map(produitMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProduitResponseDTO updateProduit(UUID id, ProduitRequestDTO dto, MultipartFile imageFile) {
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit introuvable"));
        produit.setNomProduit(dto.getNomProduit());
        produit.setDescription(dto.getDescription());
        produit.setPrixUnitaire(dto.getPrixUnitaire());
        produit.setStock(dto.getStock());

        if (dto.getCategorieId() != null) {
            Categorie categorie = categorieRepository.findById(dto.getCategorieId())
                    .orElseThrow(() -> new RuntimeException("Categorie introuvable"));
            produit.setCategorie(categorie);
        }

        if (imageFile != null && !imageFile.isEmpty()) {
            if (produit.getPhotoProduit() != null) {
                deletePhysicalFile(produit.getPhotoProduit());
            }
            String fileName = saveImage(imageFile);
            produit.setPhotoProduit(fileName);
        }

        return produitMapper.toResponse(produitRepository.save(produit));
    }

    @Override
    public void deleteProduit(UUID id) {

        Produit produit = produitRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Produit introuvable"));
        produit.setActif(false);
        produitRepository.delete(produit);
    }

    //Methode utilitaire pour suppr une photo
    private void deletePhysicalFile(String fileName) {
        try {
            Path filePath = Paths.get("uploads/produits/").resolve(fileName);
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            System.err.println("Could not delete file: " + e.getMessage());
        }
    }
}