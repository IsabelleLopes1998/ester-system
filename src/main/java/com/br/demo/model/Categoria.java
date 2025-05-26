package com.br.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    @NotNull(message = "Nome é obrigatório.")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres.")
    private String nome;

    @Column(nullable = false)
    @NotNull(message = "Descrição é obrigatória.")
    @Size(min = 5, max = 255, message = "Descrição deve ter entre 5 e 255 caracteres.")
    private String descricao;

    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL)
    @NotNull(message = "A lista de produtos não pode ser nula.")
    private List<Produto> produtos;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
