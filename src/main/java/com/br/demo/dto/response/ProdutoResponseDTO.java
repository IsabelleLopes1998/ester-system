package com.br.demo.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoResponseDTO {
    private UUID id;
    private String nome;
    private BigDecimal valor;
    private Integer quantidadeEstoque;
    private String nomeCategoria;
    private String nomeSubcategoria; // pode ser null
}
