package com.electronic.electronic.services;

import com.electronic.electronic.DTOs.Response.DashboardStatResponseDTO;

public interface StatistiqueService {

    DashboardStatResponseDTO getDashboardStats();

    Double getChiffreAffaireMensuel();

    Double getPourcentageCommandesLivrees();

    Long getNombreProduitsDisponibles();

    Long getNombreProduitsStockBas();

}
