package com.br.demo.repository;

import com.br.demo.model.HistoricoValor;
import com.br.demo.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface HistoricoValorRepository extends JpaRepository<HistoricoValor, UUID> {
    @Query(value = "SELECT * FROM historico_valor h " +
            "WHERE h.id_produto = :produtoId " +
            "AND h.data <= :data " +
            "ORDER BY h.data DESC " +
            "LIMIT 1", nativeQuery = true)
    Optional<HistoricoValor> findPrecoVigenteByProdutoAndData(
            @Param("produtoId") UUID produtoId,
            @Param("data") LocalDateTime data
    );
}