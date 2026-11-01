package com.cafesmart.agronomia;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AmostragemRepository extends JpaRepository<Amostragem, Long> {
    List<Amostragem> findByTalhaoId(Long talhaoId);

    List<Amostragem> findByTalhaoIdAndNivelAlertaNot(Long talhaoId, NivelAlerta nivelAlerta);
}