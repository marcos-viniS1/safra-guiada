package com.cafesmart.propriedade;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/lavouras")
@RequiredArgsConstructor
public class LavouraController {

    private final LavouraRepository lavouraRepository;
    private final PropriedadeRepository propriedadeRepository;

    @PostMapping("/propriedade/{propriedadeId}")
    public ResponseEntity<Lavoura> criar(@PathVariable Long propriedadeId, @RequestBody Lavoura lavoura) {
        return propriedadeRepository.findById(propriedadeId)
                .map(propriedade -> {
                    lavoura.setPropriedade(propriedade);
                    return ResponseEntity.ok(lavouraRepository.save(lavoura));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/propriedade/{propriedadeId}")
    public List<Lavoura> listarPorPropriedade(@PathVariable Long propriedadeId) {
        return lavouraRepository.findByPropriedadeId(propriedadeId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lavoura> buscarPorId(@PathVariable Long id) {
        return lavouraRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (lavouraRepository.existsById(id)) {
            lavouraRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}