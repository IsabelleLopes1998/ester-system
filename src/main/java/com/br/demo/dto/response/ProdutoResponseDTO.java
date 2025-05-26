package com.br.demo.dto.response;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    }

