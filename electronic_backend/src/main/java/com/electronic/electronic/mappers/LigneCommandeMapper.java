package com.electronic.electronic.mappers;

import com.electronic.electronic.DTOs.Request.LigneCommandeRequestDTO;
import com.electronic.electronic.DTOs.Response.LigneCommandeResponseDTO;
import com.electronic.electronic.models.LigneCommande;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = ProduitMapper.class
)
public interface LigneCommandeMapper {
    LigneCommande toEntity(LigneCommandeRequestDTO dto);

    @Mapping(source = "sousTotal", target = "sousTotal")
    @Mapping(source = "produit", target = "produit")
    LigneCommandeResponseDTO toResponse(LigneCommande ligneCommande);
}
