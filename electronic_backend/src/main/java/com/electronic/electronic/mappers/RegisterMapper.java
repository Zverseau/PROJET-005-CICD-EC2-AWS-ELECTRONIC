package com.electronic.electronic.mappers;

import com.electronic.electronic.DTOs.Request.AdminRegisterRequestDTO;
import com.electronic.electronic.models.Admin;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface RegisterMapper {
    AdminRegisterRequestDTO toDto(Admin admin);
    Admin toResponse(AdminRegisterRequestDTO registerRequestDTO);
}
