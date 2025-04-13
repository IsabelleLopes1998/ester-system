package com.br.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

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
    private Integer quantidadeVenda;

    @Column(nullable = false)
    private BigDecimal valorUnitario;
}
