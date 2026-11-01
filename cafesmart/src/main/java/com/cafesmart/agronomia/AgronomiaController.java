package com.cafesmart.agronomia;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agronomia")
@RequiredArgsConstructor
public class AgronomiaController {

    private final AgronomiaService agronomiaService;

    @PostMapping("/talhao/{talhaoId}/amostragem")
    public ResponseEntity<Amostragem> registrarAmostragem(
            @PathVariable Long talhaoId,
            @RequestBody Amostragem amostragem) {
        try {
            Amostragem salva = agronomiaService.registrarAmostragem(talhaoId, amostragem);
            return ResponseEntity.ok(salva);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/talhao/{talhaoId}/amostragens")
    public List<Amostragem> listarPorTalhao(@PathVariable Long talhaoId) {
        return agronomiaService.listarPorTalhao(talhaoId);
    }

    @GetMapping("/talhao/{talhaoId}/alertas")
    public List<Amostragem> listarAlertas(@PathVariable Long talhaoId) {
        return agronomiaService.listarAlertasPorTalhao(talhaoId);
    }
}