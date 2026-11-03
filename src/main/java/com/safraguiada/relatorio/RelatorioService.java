package com.safraguiada.relatorio;

import com.safraguiada.agronomia.AmostragemRepository;
import com.safraguiada.agronomia.NivelAlerta;
import com.safraguiada.calendario.StatusTarefa;
import com.safraguiada.calendario.TarefaRepository;
import com.safraguiada.custos.LancamentoCustoRepository;
import com.safraguiada.propriedade.Propriedade;
import com.safraguiada.propriedade.PropriedadeRepository;
import com.safraguiada.propriedade.Talhao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RelatorioService {

    private final PropriedadeRepository propriedadeRepository;
    private final TarefaRepository tarefaRepository;
    private final AmostragemRepository amostragemRepository;
    private final LancamentoCustoRepository custoRepository;

    public DashboardDTO gerarDashboardPropriedade(Long propriedadeId) {
        Propriedade propriedade = propriedadeRepository.findById(propriedadeId)
                .orElseThrow(() -> new RuntimeException("Propriedade não encontrada com ID: " + propriedadeId));

        List<Talhao> talhoes = new ArrayList<>();
        if (propriedade.getLavouras() != null) {
            propriedade.getLavouras().forEach(lavoura -> {
                if (lavoura.getTalhoes() != null) {
                    talhoes.addAll(lavoura.getTalhoes());
                }
            });
        }

        int totalLavouras = propriedade.getLavouras() != null ? propriedade.getLavouras().size() : 0;
        int totalTalhoes = talhoes.size();

        double areaTotalHectares = talhoes.stream()
                .mapToDouble(t -> t.getAreaHectares() != null ? t.getAreaHectares() : 0.0)
                .sum();

        long totalTarefas = 0;
        long tarefasPendentes = 0;
        long tarefasRealizadas = 0;
        long totalAlertasAtivos = 0;
        BigDecimal custoTotalAcumulado = BigDecimal.ZERO;

        for (Talhao talhao : talhoes) {
            var tarefas = tarefaRepository.findByTalhaoId(talhao.getId());
            totalTarefas += tarefas.size();
            tarefasPendentes += tarefas.stream().filter(t -> t.getStatus() == StatusTarefa.PREVISTA).count();
            tarefasRealizadas += tarefas.stream().filter(t -> t.getStatus() == StatusTarefa.REALIZADA).count();

            var amostragens = amostragemRepository.findByTalhaoId(talhao.getId());
            totalAlertasAtivos += amostragens.stream()
                    .filter(a -> a.getNivelAlerta() == NivelAlerta.ATENCAO || a.getNivelAlerta() == NivelAlerta.CRITICO)
                    .count();

            BigDecimal custoTalhao = custoRepository.calcularTotalCustoPorTalhao(talhao.getId());
            if (custoTalhao != null) {
                custoTotalAcumulado = custoTotalAcumulado.add(custoTalhao);
            }
        }

        BigDecimal custoMedioPorHectare = BigDecimal.ZERO;
        if (areaTotalHectares > 0) {
            custoMedioPorHectare = custoTotalAcumulado.divide(BigDecimal.valueOf(areaTotalHectares), 2,
                    RoundingMode.HALF_UP);
        }

        return new DashboardDTO(
                propriedade.getId(),
                propriedade.getNome(),
                propriedade.getProdutor(),
                totalLavouras,
                totalTalhoes,
                areaTotalHectares,
                totalTarefas,
                tarefasPendentes,
                tarefasRealizadas,
                totalAlertasAtivos,
                custoTotalAcumulado,
                custoMedioPorHectare);
    }
}