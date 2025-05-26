package com.br.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 100)
    @NotNull(message = "Nome é obrigatório.")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres.")
    private String nome;

    @Column(nullable = false, unique = true, length = 11)
    @NotNull(message = "CPF é obrigatório.")
    private String cpf;

    @Column(nullable = false)
    @NotNull(message = "Data de nascimento é obrigatória.")
    private LocalDate dataNascimento;

    @Column(nullable = false, unique = true, length = 120)
    @NotNull(message = "Nome de usuário é obrigatório.")
    private String username;

    @Column(nullable = false, length = 60)
    @NotNull(message = "Senha é obrigatória.")
    @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres.")
    private String senha;

    @Column(nullable = false, length = 15)
    @NotNull(message = "Telefone principal é obrigatório.")
    private String telefonePrincipal;

    @Column(length = 15)
    private String telefoneSecundario;

    @ManyToOne
    @JoinColumn(name = "id_cargo", nullable = false)
    @NotNull(message = "Cargo é obrigatório.")
    private Cargo cargo;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Aqui, você pode dinamicamente mapear os cargos para permissões
        return List.of(new SimpleGrantedAuthority("ROLE_" + cargo.getNome().toUpperCase()));
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
