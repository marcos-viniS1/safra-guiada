package com.cafesmart.custos;

import com.cafesmart.propriedade.Talhao;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "lancamentos_custo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LancamentoCusto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(nullable = false)
    private LocalDate dataLancamento = LocalDate.now();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoCusto tipoCusto;

    @ManyToOne
    @JoinColumn(name = "talhao_id", nullable = false)
    @JsonIgnoreProperties("custos")
    private Talhao talhao;
}