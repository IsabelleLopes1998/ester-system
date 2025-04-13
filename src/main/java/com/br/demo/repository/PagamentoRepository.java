package com.br.demo.repository;

import com.br.demo.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface PagamentoRepository extends JpaRepository<Pagamento, UUID> {
}
