package com.electronic.electronic.DTOs.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminRequestDTO {
    private UUID id;
    private String email;
    private String nomAdmin;
    private String prenomAdmin;
    private String role;
}
