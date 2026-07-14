package com.electronic.electronic.mappers;

import com.electronic.electronic.DTOs.Request.CommandeRequestDTO;
import com.electronic.electronic.DTOs.Response.CommandeResponseDTO;
import com.electronic.electronic.models.Commande;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {LigneCommandeMapper.class, PaiementMapper.class}
)
public interface CommandeMapper {
    Commande toEntity(CommandeRequestDTO dto);
    @Mapping(source = "paiement", target = "paiement")
    @Mapping(source = "lignesCommande", target = "lignesCommande")
    CommandeResponseDTO toResponse(Commande commande);
}
