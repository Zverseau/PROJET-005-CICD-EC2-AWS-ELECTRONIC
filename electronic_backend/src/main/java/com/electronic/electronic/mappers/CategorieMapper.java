package com.electronic.electronic.mappers;

import com.electronic.electronic.DTOs.Request.CategorieRequestDTO;
import com.electronic.electronic.DTOs.Response.CategorieResponseDTO;
import com.electronic.electronic.models.Categorie;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CategorieMapper {
    Categorie toEntity(CategorieRequestDTO dto);
    CategorieResponseDTO toResponse(Categorie categorie);
}
