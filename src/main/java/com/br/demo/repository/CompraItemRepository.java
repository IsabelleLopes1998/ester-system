package com.br.demo.repository;

import com.br.demo.model.CompraItem;
import com.br.demo.model.CompraItemId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompraItemRepository extends JpaRepository<CompraItem, CompraItemId> {
}
