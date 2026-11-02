package com.safraguiada.custos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResumoCustosDTO {
    private Long talhaoId;
    private String nomeTalhao;
    private Double areaHectares;
    private BigDecimal custoTotal;
    private BigDecimal custoPorHectare;
    private BigDecimal custoPorSaca;
}