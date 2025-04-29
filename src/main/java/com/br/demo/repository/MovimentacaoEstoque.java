package com.br.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MovimentacaoEstoque extends JpaRepository<com.br.demo.model.MovimentacaoEstoque, UUID> {
}