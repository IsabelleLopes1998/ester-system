package com.br.demo.repository;

import com.br.demo.model.MovimentacaoEstoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface MovimentacaoEstoqueRepository extends JpaRepository<com.br.demo.model.MovimentacaoEstoque, UUID> {
    List<MovimentacaoEstoque> findByVendaId(UUID vendaId);

    @Modifying
    @Query("DELETE FROM MovimentacaoEstoque me WHERE me.venda.id = :vendaId")
    void deleteByVendaId(@Param("vendaId") UUID vendaId);

}