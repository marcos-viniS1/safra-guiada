package com.safraguiada.propriedade;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lavoura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @ManyToOne
    @JoinColumn(name = "propriedade_id")
    private Propriedade propriedade;

    // Adicione ou confirme este relacionamento:
    @OneToMany(mappedBy = "lavoura", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Talhao> talhoes = new ArrayList<>();
}