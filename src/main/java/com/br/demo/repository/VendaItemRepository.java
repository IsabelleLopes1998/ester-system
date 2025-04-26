package com.br.demo.repository;

import com.br.demo.model.VendaItem;
import com.br.demo.model.VendaItemId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendaItemRepository extends JpaRepository<VendaItem, VendaItemId> {
}