package com.safraguiada.calendario;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    List<Tarefa> findByTalhaoId(Long talhaoId);

    List<Tarefa> findByTalhaoIdAndMesPrevisto(Long talhaoId, Integer mesPrevisto);

    List<Tarefa> findByStatus(StatusTarefa status);
}