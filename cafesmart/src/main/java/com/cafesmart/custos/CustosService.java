package com.cafesmart.custos;

import com.cafesmart.propriedade.Talhao;
import com.cafesmart.propriedade.TalhaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustosService {

    private final LancamentoCustoRepository custoRepository;
    private final TalhaoRepository talhaoRepository;

    public LancamentoCusto registrarCusto(Long talhaoId, LancamentoCusto custo) {
        Talhao talhao = talhaoRepository.findById(talhaoId)
                .orElseThrow(() -> new RuntimeException("Talhão não encontrado com ID: " + talhaoId));

        if (custo.getDataLancamento() == null) {
            custo.setDataLancamento(LocalDate.now());
        }

        custo.setTalhao(talhao);
        return custoRepository.save(custo);
    }

    public List<LancamentoCusto> listarPorTalhao(Long talhaoId) {
        return custoRepository.findByTalhaoId(talhaoId);
    }

    public ResumoCustosDTO obterResumoCustos(Long talhaoId, Integer sacasColhidas) {
        Talhao talhao = talhaoRepository.findById(talhaoId)
                .orElseThrow(() -> new RuntimeException("Talhão não encontrado com ID: " + talhaoId));

        BigDecimal custoTotal = custoRepository.calcularTotalCustoPorTalhao(talhaoId);
        if (custoTotal == null) {
            custoTotal = BigDecimal.ZERO;
        }

        BigDecimal custoPorHectare = BigDecimal.ZERO;
        if (talhao.getAreaHectares() != null && talhao.getAreaHectares() > 0) {
            custoPorHectare = custoTotal.divide(BigDecimal.valueOf(talhao.getAreaHectares()), 2, RoundingMode.HALF_UP);
        }

        BigDecimal custoPorSaca = BigDecimal.ZERO;
        if (sacasColhidas != null && sacasColhidas > 0) {
            custoPorSaca = custoTotal.divide(BigDecimal.valueOf(sacasColhidas), 2, RoundingMode.HALF_UP);
        }

        return new ResumoCustosDTO(
                talhao.getId(),
                talhao.getNome(),
                talhao.getAreaHectares(),
                custoTotal,
                custoPorHectare,
                custoPorSaca);
    }
}