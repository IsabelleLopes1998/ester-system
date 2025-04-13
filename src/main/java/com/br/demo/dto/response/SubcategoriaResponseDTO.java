package com.br.demo.dto.response;

import lombok.*;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SubcategoriaResponseDTO {
    private UUID id;
    private String nome;
    private String descricao;
    private String nomeCategoria;
}
