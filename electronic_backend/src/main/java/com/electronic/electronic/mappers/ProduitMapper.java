package com.electronic.electronic.mappers;

import com.electronic.electronic.DTOs.Request.ProduitRequestDTO;
import com.electronic.electronic.DTOs.Response.ProduitResponseDTO;
import com.electronic.electronic.models.Produit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ProduitMapper {
    Produit toEntity(ProduitRequestDTO dto);
    //@Mapping(source = "categorie.id", target = "categorieId")
    ProduitResponseDTO toResponse(Produit produit);
}
