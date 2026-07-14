package com.electronic.electronic.controllers;

import com.electronic.electronic.DTOs.Request.ProduitRequestDTO;
import com.electronic.electronic.DTOs.Response.ProduitResponseDTO;
import com.electronic.electronic.services.ProduitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/produits")
@RequiredArgsConstructor
public class ProduitController {

    private final ProduitService produitService;

    @PostMapping(value = "/create",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProduitResponseDTO> createProduit(
            @RequestPart("produit") ProduitRequestDTO dto,
            @RequestPart(value = "image", required = false) MultipartFile image) {

        return new ResponseEntity<>(produitService.createProduit(dto, image), HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ProduitResponseDTO>> getAllProduits() {
        return ResponseEntity.ok(produitService.getAllProduits());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProduit(@PathVariable UUID id) {
        produitService.deleteProduit(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<ProduitResponseDTO> getProduitById(@PathVariable UUID id) {
        return ResponseEntity.ok(produitService.getProduitById(id));
    }

    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProduitResponseDTO> updateProduit(
            @PathVariable UUID id,
            @RequestPart("produit") ProduitRequestDTO dto,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        return ResponseEntity.ok(produitService.updateProduit(id, dto, image));
    }

//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<Void> deleteProduit(@PathVariable UUID id) {
//        produitService.deleteProduit(id);
//        return ResponseEntity.noContent().build();
//    }
}
