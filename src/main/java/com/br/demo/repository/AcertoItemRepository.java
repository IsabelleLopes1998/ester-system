package com.br.demo.repository;

import com.br.demo.model.AcertoItem;
import com.br.demo.model.AcertoItemId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcertoItemRepository extends JpaRepository<AcertoItem, AcertoItemId> {
}