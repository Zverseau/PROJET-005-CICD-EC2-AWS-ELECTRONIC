package com.electronic.electronic.mappers;

import com.electronic.electronic.DTOs.Request.AdminRequestDTO;
import com.electronic.electronic.DTOs.Response.AdminResponseDTO;
import com.electronic.electronic.models.Admin;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface AdminMapper {

    Admin toEntity(AdminRequestDTO dto);
    AdminResponseDTO toResponse(Admin admin);
}
