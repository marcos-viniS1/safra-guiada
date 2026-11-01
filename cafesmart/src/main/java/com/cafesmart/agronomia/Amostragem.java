package com.cafesmart.agronomia;

import com.cafesmart.propriedade.Talhao;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "amostragens")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Amostragem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate dataAmostragem = LocalDate.now();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoPragaDoenca tipoPragaDoenca;

    @Column(nullable = false)
    private Double percentualInfestacao;

    @Enumerated(EnumType.STRING)
    private NivelAlerta nivelAlerta;

    private String observacoes;

    @ManyToOne
    @JoinColumn(name = "talhao_id", nullable = false)
    @JsonIgnoreProperties("amostragens")
    private Talhao talhao;
}