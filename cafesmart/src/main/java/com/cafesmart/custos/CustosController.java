package com.cafesmart.custos;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/custos")
@RequiredArgsConstructor
public class CustosController {

    private final CustosService custosService;

    @PostMapping("/talhao/{talhaoId}")
    public ResponseEntity<LancamentoCusto> registrarCusto(
            @PathVariable Long talhaoId,
            @RequestBody LancamentoCusto custo) {
        try {
            LancamentoCusto novoCusto = custosService.registrarCusto(talhaoId, custo);
            return ResponseEntity.ok(novoCusto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/talhao/{talhaoId}")
    public List<LancamentoCusto> listarPorTalhao(@PathVariable Long talhaoId) {
        return custosService.listarPorTalhao(talhaoId);
    }

    @GetMapping("/talhao/{talhaoId}/resumo")
    public ResponseEntity<ResumoCustosDTO> obterResumo(
            @PathVariable Long talhaoId,
            @RequestParam(required = false) Integer sacasColhidas) {
        try {
            ResumoCustosDTO resumo = custosService.obterResumoCustos(talhaoId, sacasColhidas);
            return ResponseEntity.ok(resumo);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}