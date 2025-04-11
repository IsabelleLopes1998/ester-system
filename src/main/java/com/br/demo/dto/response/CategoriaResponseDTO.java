package com.br.demo.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaResponseDTO {
    private UUID id;
    private String nome;
    private String descricao;
}

