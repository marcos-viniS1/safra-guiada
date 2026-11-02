package com.safraguiada.propriedade;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "propriedades")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Propriedade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String produtor;
    private String localizacao;

    @OneToMany(mappedBy = "propriedade", cascade = CascadeType.ALL)
    private List<Lavoura> lavouras;
}