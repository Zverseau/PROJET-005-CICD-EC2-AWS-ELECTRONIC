package com.electronic.electronic.mappers;

import com.electronic.electronic.DTOs.Request.PaiementRequestDTO;
import com.electronic.electronic.DTOs.Response.PaiementResponseDTO;
import com.electronic.electronic.models.Paiement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface PaiementMapper {
    Paiement toEntity(PaiementRequestDTO dto);
    @Mapping(source = "commande.id", target = "commandeId")
    @Mapping(source = "createDate", target = "createdAt")
    PaiementResponseDTO toResponse(Paiement paiement);
}
