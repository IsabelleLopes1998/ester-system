package com.br.demo.repository;

import com.br.demo.model.HistoricoValor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HistoricoValorRepository extends JpaRepository<HistoricoValor, UUID> {}