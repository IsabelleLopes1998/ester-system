package com.br.demo.dto.request;


import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompraRequestDTO {
    private LocalDate data;
    private String fornecedor;
    private UUID usuarioId;
    private UUID pagamentoId;
    private Integer quantidadeParcelas;
    private List<CompraItemRequestDTO> itens; // Lista de produtos comprados
}
