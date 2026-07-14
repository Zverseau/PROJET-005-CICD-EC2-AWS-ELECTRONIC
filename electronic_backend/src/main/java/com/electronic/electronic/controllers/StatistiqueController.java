package com.electronic.electronic.controllers;

import com.electronic.electronic.DTOs.Response.DashboardStatResponseDTO;
import com.electronic.electronic.services.StatistiqueService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/statistiques")
@RequiredArgsConstructor
public class StatistiqueController {

    private final StatistiqueService statistiqueService;

    @GetMapping("/dashboard")
    public DashboardStatResponseDTO getDashboardStats() {
        return statistiqueService.getDashboardStats();
    }

}