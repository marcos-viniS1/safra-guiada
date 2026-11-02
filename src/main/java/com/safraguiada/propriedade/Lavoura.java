package com.safraguiada.propriedade;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "lavouras")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lavoura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @ManyToOne
    @JoinColumn(name = "propriedade_id", nullable = false)
    @JsonIgnoreProperties("lavouras") // Interrompe o loop circular
    private Propriedade propriedade;
}