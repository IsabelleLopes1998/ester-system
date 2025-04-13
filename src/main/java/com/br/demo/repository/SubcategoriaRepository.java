package com.br.demo.repository;

import com.br.demo.model.Subcategoria;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SubcategoriaRepository extends JpaRepository<Subcategoria, UUID> {
}
