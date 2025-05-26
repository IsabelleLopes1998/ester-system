package com.br.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
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

    @NotNull(message = "Produto é obrigatório.")
    @ManyToOne
    @MapsId("produtoId")
    @JoinColumn(name = "id_produto", nullable = false)
    private Produto produto;

    @NotNull(message = "Compra é obrigatória.")
    @ManyToOne
    @MapsId("compraId")
    @JoinColumn(name = "id_compra", nullable = false)
    private Compra compra;

    @NotNull(message = "Quantidade é obrigatória.")
    @Min(value = 1, message = "Quantidade deve ser maior que zero.")
    @Column(nullable = false)
    private Integer quantidade;

    @NotNull(message = "Preço unitário é obrigatório.")
    @DecimalMin(value = "0.01", inclusive = true, message = "O valor não pode ser menor que 0.01.")
    @Column(nullable = false)
    private BigDecimal preçoUnitario;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
