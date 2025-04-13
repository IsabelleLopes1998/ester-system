package com.br.demo.dto.request;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubcategoriaRequestDTO {
    private String nome;
    private String descricao;
    private UUID idCategoria;
}
