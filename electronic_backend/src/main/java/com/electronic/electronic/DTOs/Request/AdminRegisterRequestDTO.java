package com.electronic.electronic.DTOs.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminRegisterRequestDTO extends RegisterRequestDTO{
    private UUID id;
    private String nomAdmin;
    private String prenomAdmin;

}