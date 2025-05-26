package com.br.demo.repository;

import com.br.demo.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    // Custom query to find a user by username
    Optional<Usuario> findByUsername(String username);
}