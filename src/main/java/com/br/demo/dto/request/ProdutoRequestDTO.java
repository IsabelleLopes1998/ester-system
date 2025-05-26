package com.br.demo.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoRequestDTO {
    private String nome;
    private BigDecimal valor;
    private Integer quantidadeEstoque;
    private UUID idCategoria;
    private UUID idSubcategoria; // pode ser null
}
