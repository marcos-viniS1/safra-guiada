package com.safraguiada.relatorio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardDTO {
    private Long propriedadeId;
    private String nomePropriedade;
    private String produtor;
    private Integer totalLavouras;
    private Integer totalTalhoes;
    private Double areaTotalHectares;

    // Indicadores do Calendário
    private Long totalTarefas;
    private Long tarefasPendentes;
    private Long tarefasRealizadas;

    // Indicadores Agronômicos
    private Long totalAlertasAtivos;

    // Indicadores Financeiros
    private BigDecimal custoTotalAcumulado;
    private BigDecimal custoMedioPorHectare;
}