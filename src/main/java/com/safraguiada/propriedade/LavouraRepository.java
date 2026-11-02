package com.safraguiada.propriedade;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LavouraRepository extends JpaRepository<Lavoura, Long> {
    List<Lavoura> findByPropriedadeId(Long propriedadeId);
}