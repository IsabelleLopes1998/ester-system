package com.br.demo.repository;

import com.br.demo.model.VendaItem;
import com.br.demo.model.VendaItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface VendaItemRepository extends JpaRepository<VendaItem, VendaItemId> {

    @Modifying
    @Query("DELETE FROM VendaItem vi WHERE vi.venda.id = :vendaId")
    void deleteByVendaId(@Param("vendaId") UUID vendaId);
}