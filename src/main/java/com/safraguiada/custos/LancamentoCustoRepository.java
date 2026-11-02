package com.safraguiada.custos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface LancamentoCustoRepository extends JpaRepository<LancamentoCusto, Long> {
    List<LancamentoCusto> findByTalhaoId(Long talhaoId);

    @Query("SELECT SUM(c.valor) FROM LancamentoCusto c WHERE c.talhao.id = :talhaoId")
    BigDecimal calcularTotalCustoPorTalhao(@Param("talhaoId") Long talhaoId);
}