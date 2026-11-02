package com.safraguiada.calendario;

import com.safraguiada.propriedade.TalhaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tarefas")
@RequiredArgsConstructor
public class TarefaController {

    private final TarefaRepository tarefaRepository;
    private final TalhaoRepository talhaoRepository;
    private final CalendarioService calendarioService;

    @PostMapping("/talhao/{talhaoId}")
    public ResponseEntity<Tarefa> criar(@PathVariable Long talhaoId, @RequestBody Tarefa tarefa) {
        return talhaoRepository.findById(talhaoId)
                .map(talhao -> {
                    tarefa.setTalhao(talhao);
                    return ResponseEntity.ok(tarefaRepository.save(tarefa));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/talhao/{talhaoId}/gerar-manejo-padrao")
    public ResponseEntity<List<Tarefa>> gerarManejoPadrao(@PathVariable Long talhaoId) {
        try {
            List<Tarefa> tarefas = calendarioService.gerarManejoPadrao(talhaoId);
            return ResponseEntity.ok(tarefas);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/talhao/{talhaoId}")
    public List<Tarefa> listarPorTalhao(@PathVariable Long talhaoId) {
        return tarefaRepository.findByTalhaoId(talhaoId);
    }

    @GetMapping("/talhao/{talhaoId}/mes/{mes}")
    public List<Tarefa> listarPorMes(@PathVariable Long talhaoId, @PathVariable Integer mes) {
        return tarefaRepository.findByTalhaoIdAndMesPrevisto(talhaoId, mes);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Tarefa> atualizarStatus(
            @PathVariable Long id,
            @RequestParam StatusTarefa status) {
        return tarefaRepository.findById(id)
                .map(tarefa -> {
                    tarefa.setStatus(status);
                    return ResponseEntity.ok(tarefaRepository.save(tarefa));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}