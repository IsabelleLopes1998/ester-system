package com.br.demo.repository;

import com.br.demo.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface VendaRepository extends JpaRepository <Venda, UUID> {


}
