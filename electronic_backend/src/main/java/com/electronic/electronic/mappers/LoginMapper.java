package com.electronic.electronic.mappers;

import com.electronic.electronic.DTOs.Request.LoginRequestDTO;
import com.electronic.electronic.models.UserBase;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface LoginMapper {

    UserBase toDto(LoginRequestDTO loginRequestDTO);
    LoginRequestDTO toResponse(UserBase userBase);
}
