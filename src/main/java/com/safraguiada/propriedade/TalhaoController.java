package com.safraguiada.propriedade;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/talhoes")
@RequiredArgsConstructor
public class TalhaoController {

    private final TalhaoRepository talhaoRepository;
    private final LavouraRepository lavouraRepository;

    @PostMapping("/lavoura/{lavouraId}")
    public ResponseEntity<Talhao> criar(@PathVariable Long lavouraId, @RequestBody Talhao talhao) {
        return lavouraRepository.findById(lavouraId)
                .map(lavoura -> {
                    talhao.setLavoura(lavoura);
                    return ResponseEntity.ok(talhaoRepository.save(talhao));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/lavoura/{lavouraId}")
    public List<Talhao> listarPorLavoura(@PathVariable Long lavouraId) {
        return talhaoRepository.findByLavouraId(lavouraId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Talhao> buscarPorId(@PathVariable Long id) {
        return talhaoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (talhaoRepository.existsById(id)) {
            talhaoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}