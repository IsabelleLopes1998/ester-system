package com.br.demo.repository;

import com.br.demo.model.AcertoEstoque;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AcertoEstoqueRepository extends JpaRepository<AcertoEstoque, UUID> {
}