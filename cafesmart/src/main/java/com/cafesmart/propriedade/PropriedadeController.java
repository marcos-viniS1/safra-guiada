package com.cafesmart.propriedade;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/propriedades")
@RequiredArgsConstructor
public class PropriedadeController {

    private final PropriedadeRepository repository;

    @PostMapping
    public Propriedade registar(@RequestBody Propriedade propriedade) {
        return repository.save(propriedade);
    }

    @GetMapping
    public List<Propriedade> listar() {
        return repository.findAll();
    }
}