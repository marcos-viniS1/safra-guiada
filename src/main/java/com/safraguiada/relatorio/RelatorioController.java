package com.safraguiada.relatorio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/relatorio")
@RequiredArgsConstructor
@Tag(name = "Relatórios e Dashboard", description = "Endpoints para indicadores consolidados da propriedade")
public class RelatorioController {

    private final RelatorioService relatorioService;

    @GetMapping("/dashboard/propriedade/{propriedadeId}")
    @Operation(summary = "Obter Raio-X consolidado da propriedade", description = "Retorna área total, total de talhões, status de tarefas, alertas agrícolas e custos acumulados.")
    public ResponseEntity<DashboardDTO> obterDashboard(@PathVariable Long propriedadeId) {
        try {
            DashboardDTO dashboard = relatorioService.gerarDashboardPropriedade(propriedadeId);
            return ResponseEntity.ok(dashboard);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}