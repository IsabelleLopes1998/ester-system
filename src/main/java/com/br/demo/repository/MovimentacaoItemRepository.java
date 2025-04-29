package com.br.demo.repository;

import com.br.demo.model.AcertoItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AcertoItemRepository extends JpaRepository<AcertoItem, UUID> {
}