package com.electronic.electronic.DTOs.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DashboardStatResponseDTO {
    private Double chiffreAffaireMensuel;

    private Double pourcentageCommandesLivrees;

    private Long produitsDisponibles;

    private Long produitsStockBas;
}
