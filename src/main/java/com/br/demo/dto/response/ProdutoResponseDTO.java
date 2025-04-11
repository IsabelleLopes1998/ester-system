package com.br.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoResponseDTO {
    private UUID id;
    private String nome;
    private double preco;
    private String categoriaNome;
}

