package com.safraguiada.propriedade;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TalhaoRepository extends JpaRepository<Talhao, Long> {
    List<Talhao> findByLavouraId(Long lavouraId);
}