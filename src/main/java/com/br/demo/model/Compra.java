package com.br.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull(message = "A data da compra é obrigatória.")
    @PastOrPresent(message = "A data da compra não pode ser no futuro.")
    @Column(nullable = false)
    private LocalDateTime data;

    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @NotEmpty(message = "A lista de itens da compra não pode estar vazia.")
    private List<CompraItem> compraItens;

    @Size(max = 255, message = "O nome do fornecedor não pode ultrapassar 255 caracteres.")
    @Column
    private String fornecedor;

    @NotNull(message = "O usuário é obrigatório.")
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
