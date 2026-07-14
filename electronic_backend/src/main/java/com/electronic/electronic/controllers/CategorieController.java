package com.electronic.electronic.controllers;

import com.electronic.electronic.DTOs.Request.CategorieRequestDTO;
import com.electronic.electronic.DTOs.Response.CategorieResponseDTO;
import com.electronic.electronic.services.CategorieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategorieController {

    private final CategorieService categorieService;

    @PostMapping("/create")
    public ResponseEntity<CategorieResponseDTO> create(@RequestBody CategorieRequestDTO dto) {
        return new ResponseEntity<>(categorieService.createCategorie(dto), HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CategorieResponseDTO>> getAll() {
        return ResponseEntity.ok(categorieService.getAllCategories());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        categorieService.deleteCategorie(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<CategorieResponseDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(categorieService.getCategorieById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CategorieResponseDTO> update(@PathVariable UUID id, @RequestBody CategorieRequestDTO dto) {
        return ResponseEntity.ok(categorieService.updateCategorie(id, dto));
    }

//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<Void> delete(@PathVariable UUID id) {
//        categorieService.deleteCategorie(id);
//        return ResponseEntity.noContent().build();
//    }
}
