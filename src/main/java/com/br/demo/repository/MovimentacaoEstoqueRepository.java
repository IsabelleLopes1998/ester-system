package com.br.demo.repository;

import com.br.demo.model.MovimentacaoEstoque;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MovimentacaoEstoqueRepository extends JpaRepository<com.br.demo.model.MovimentacaoEstoque, UUID> {
    List<MovimentacaoEstoque> findByVendaId(UUID vendaId);
}