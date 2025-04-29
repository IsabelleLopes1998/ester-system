package com.br.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CompraItem {

    @EmbeddedId
    private CompraItemId id;

    @ManyToOne
    @MapsId("produtoId")
    @JoinColumn(name = "id_produto", nullable = false)
    private Produto produto;

    @ManyToOne
    @MapsId("compraId")
    @JoinColumn(name = "id_compra", nullable = false)
    private Compra compra;

    @Column(nullable = false)
    @NotNull(message = "Quantidade é obrigatório.")
    private Integer quantidade;

    @Column(nullable = false)
    private BigDecimal preçoUnitario;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
