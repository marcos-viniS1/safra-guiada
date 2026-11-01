package com.cafesmart.calendario;

import com.cafesmart.propriedade.Talhao;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "tarefas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    private String descricao;

    private Integer mesPrevisto; // Representa o mês do ciclo (1 a 12)

    @Enumerated(EnumType.STRING)
    private StatusTarefa status = StatusTarefa.PREVISTA;

    private LocalDate dataExecucao;

    private String observacoes;

    @ManyToOne
    @JoinColumn(name = "talhao_id", nullable = false)
    @JsonIgnoreProperties("tarefas")
    private Talhao talhao;
}