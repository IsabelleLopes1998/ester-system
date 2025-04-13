package com.br.demo.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompraResponseDTO {
    private UUID id;
    private LocalDate data;
    private String fornecedor;
    private String nomeUsuario;
    private String formaPagamento;
    private Integer quantidadeParcelas;
    private List<CompraItemResponseDTO> itens;
}
