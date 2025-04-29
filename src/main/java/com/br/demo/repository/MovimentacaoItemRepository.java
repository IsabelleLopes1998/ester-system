package com.br.demo.repository;

import com.br.demo.model.MovimentacaoEstoque;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MovimentacaoItemRepository extends JpaRepository<MovimentacaoEstoque, UUID> {
}